package io.github.seggan.slimefunwarfare;

import io.github.seggan.slimefunwarfare.lists.Items;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/**
 * Optional ItemsAdder bridge used only to borrow an ItemsAdder item's visual ItemStack.
 *
 * <p>Slimefun remains authoritative for item identity and Warfare remains authoritative
 * for weapon behaviour. No ItemsAdder weapon behaviour is invoked by this class.</p>
 */
public final class ItemsAdderIntegration {

    private static final String CONFIG_ROOT = "itemsadder.visuals";
    private static final long READY_TIMEOUT_TICKS = 600L;

    private ItemsAdderIntegration() {
    }

    /**
     * Runs the supplied continuation once ItemsAdder's custom item registry is ready.
     *
     * <p>ItemsAdder loads its content asynchronously, so Bukkit/Paper plugin enable order alone
     * is not enough. Warfare must wait for ItemsAdderLoadDataEvent before borrowing model data,
     * otherwise every CustomStack lookup can return null even though the configured IDs are valid.</p>
     */
    public static void runWhenReady(SlimefunWarfare plugin, Runnable continuation) {
        if (!plugin.getConfig().getBoolean(CONFIG_ROOT + ".enabled", true)) {
            continuation.run();
            return;
        }

        Plugin itemsAdder = plugin.getServer().getPluginManager().getPlugin("ItemsAdder");
        if (itemsAdder == null) {
            plugin.getLogger().info("ItemsAdder visual integration not enabled: ItemsAdder is not installed.");
            continuation.run();
            return;
        }

        AtomicBoolean finished = new AtomicBoolean(false);
        Listener[] loadListener = new Listener[1];

        Runnable finish = () -> {
            if (!finished.compareAndSet(false, true)) {
                return;
            }

            if (loadListener[0] != null) {
                HandlerList.unregisterAll(loadListener[0]);
            }

            applyVisuals(plugin);
            continuation.run();
        };

        try {
            loadListener[0] = registerLoadDataListener(plugin, finish);
            plugin.getLogger().info("Waiting for ItemsAdder custom items to finish loading before registering Warfare visuals.");
        } catch (ReflectiveOperationException | LinkageError ex) {
            plugin.getLogger().log(
                Level.WARNING,
                "ItemsAdder was detected but its load-data event API could not be registered. Warfare will probe its registry instead.",
                ex
            );
        }

        // Register the listener first, then probe. This closes the race where ItemsAdder could
        // finish loading between an initial registry check and event-listener registration.
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            if (hasAnyConfiguredVisual(plugin)) {
                finish.run();
            }
        });

        // Fallback for unusual ItemsAdder builds where the load-data event was already fired or
        // cannot be observed. These probes are cheap and only run during server startup.
        long[] probes = {20L, 100L, 300L};
        for (long delay : probes) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (!finished.get() && hasAnyConfiguredVisual(plugin)) {
                    finish.run();
                }
            }, delay);
        }

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (!finished.get()) {
                plugin.getLogger().warning(
                    "ItemsAdder readiness was not observed within 30 seconds. Warfare will continue with any visuals currently available."
                );
                finish.run();
            }
        }, READY_TIMEOUT_TICKS);
    }

    @SuppressWarnings("unchecked")
    private static Listener registerLoadDataListener(SlimefunWarfare plugin, Runnable finish)
        throws ReflectiveOperationException {
        Class<?> rawEventClass = Class.forName("dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent");
        if (!Event.class.isAssignableFrom(rawEventClass)) {
            throw new ClassCastException("ItemsAdderLoadDataEvent is not a Bukkit Event");
        }

        Class<? extends Event> eventClass = (Class<? extends Event>) rawEventClass;
        Listener listener = new Listener() {
        };

        plugin.getServer().getPluginManager().registerEvent(
            eventClass,
            listener,
            EventPriority.MONITOR,
            (ignored, event) -> plugin.getServer().getScheduler().runTask(plugin, finish),
            plugin,
            true
        );
        return listener;
    }

    private static boolean hasAnyConfiguredVisual(SlimefunWarfare plugin) {
        ConfigurationSection mappings = plugin.getConfig().getConfigurationSection(CONFIG_ROOT + ".mappings");
        if (mappings == null) {
            return false;
        }

        try {
            Class<?> customStackClass = Class.forName("dev.lone.itemsadder.api.CustomStack");
            Method getInstance = customStackClass.getMethod("getInstance", String.class);

            for (String slimefunId : supportedItems().keySet()) {
                String itemsAdderId = mappings.getString(slimefunId, "").trim();
                if (itemsAdderId.isEmpty()) {
                    continue;
                }

                if (getInstance.invoke(null, itemsAdderId) != null) {
                    return true;
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | LinkageError ex) {
            return false;
        }

        return false;
    }

    public static void applyVisuals(SlimefunWarfare plugin) {
        if (!plugin.getConfig().getBoolean(CONFIG_ROOT + ".enabled", true)) {
            return;
        }

        if (!plugin.getServer().getPluginManager().isPluginEnabled("ItemsAdder")) {
            plugin.getLogger().info("ItemsAdder visual integration not enabled: ItemsAdder is not installed.");
            return;
        }

        ConfigurationSection mappings = plugin.getConfig().getConfigurationSection(CONFIG_ROOT + ".mappings");
        if (mappings == null) {
            plugin.getLogger().warning("ItemsAdder is installed, but no Warfare visual mappings are configured.");
            return;
        }

        try {
            Class<?> customStackClass = Class.forName("dev.lone.itemsadder.api.CustomStack");
            Method getInstance = customStackClass.getMethod("getInstance", String.class);
            Method getItemStack = customStackClass.getMethod("getItemStack");

            int applied = 0;
            for (Map.Entry<String, SlimefunItemStack> entry : supportedItems().entrySet()) {
                String slimefunId = entry.getKey();
                String itemsAdderId = mappings.getString(slimefunId, "").trim();
                if (itemsAdderId.isEmpty()) {
                    continue;
                }

                if (applyVisual(plugin, entry.getValue(), itemsAdderId, getInstance, getItemStack)) {
                    applied++;
                }
            }

            plugin.getLogger().info("ItemsAdder visual integration applied to " + applied + " Warfare item(s).");
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            plugin.getLogger().log(
                Level.WARNING,
                "ItemsAdder was detected but its CustomStack API could not be loaded. Warfare will use vanilla visuals.",
                ex
            );
        }
    }

    private static boolean applyVisual(
        SlimefunWarfare plugin,
        SlimefunItemStack target,
        String itemsAdderId,
        Method getInstance,
        Method getItemStack
    ) {
        try {
            Object customStack = getInstance.invoke(null, itemsAdderId);
            if (customStack == null) {
                plugin.getLogger().warning(
                    "ItemsAdder visual '" + itemsAdderId + "' was not found for Warfare item " + target.getItemId() + "."
                );
                return false;
            }

            Object rawStack = getItemStack.invoke(customStack);
            if (!(rawStack instanceof ItemStack visual)) {
                plugin.getLogger().warning(
                    "ItemsAdder visual '" + itemsAdderId + "' did not return an ItemStack for " + target.getItemId() + "."
                );
                return false;
            }

            ItemMeta visualMeta = visual.getItemMeta();
            if (visualMeta == null) {
                plugin.getLogger().warning(
                    "ItemsAdder visual '" + itemsAdderId + "' has no item meta; keeping the vanilla Warfare visual."
                );
                return false;
            }

            ItemMeta originalMeta = target.getItemMeta();
            String displayName = originalMeta != null && originalMeta.hasDisplayName()
                ? originalMeta.getDisplayName()
                : null;
            List<String> lore = originalMeta != null && originalMeta.hasLore()
                ? originalMeta.getLore()
                : null;
            int amount = target.getAmount();

            target.setType(visual.getType());

            // Keep Warfare's player-facing name/lore while retaining ItemsAdder's model metadata.
            if (displayName != null) {
                visualMeta.setDisplayName(displayName);
            }
            if (lore != null) {
                visualMeta.setLore(lore);
            }

            // Add the Slimefun identity on top of the ItemsAdder ItemStack. This is what keeps
            // Slimefun recipes, getByItem(), Warfare handlers and existing IDs authoritative.
            Slimefun.getItemDataService().setItemData(visualMeta, target.getItemId());
            target.setItemMeta(visualMeta);
            target.setAmount(amount);
            return true;
        } catch (IllegalAccessException | InvocationTargetException ex) {
            plugin.getLogger().log(
                Level.WARNING,
                "Failed to apply ItemsAdder visual '" + itemsAdderId + "' to " + target.getItemId() + ".",
                ex
            );
            return false;
        }
    }

    private static Map<String, SlimefunItemStack> supportedItems() {
        Map<String, SlimefunItemStack> items = new LinkedHashMap<>();
        items.put("GUN_PISTOL", Items.PISTOL);
        items.put("GUN_REVOLVER", Items.REVOLVER);
        items.put("GUN_MACHINE_GUN", Items.MACHINE_GUN);
        items.put("GUN_MINIGUN", Items.MINIGUN);
        items.put("GUN_RIFLE", Items.RIFLE);
        items.put("GUN_SHOTGUN", Items.SHOTGUN);
        items.put("GUN_ASSAULT_RIFLE", Items.ASSAULT_RIFLE);
        items.put("GUN_SNIPER", Items.SNIPER);
        items.put("GUN_ENERGY_RIFLE", Items.ENERGY_RIFLE);
        items.put("GRENADE", Items.EMPTY_GRENADE);
        items.put("IRON_BULLET", Items.IRON_BULLET);
        items.put("LEAD_BULLET", Items.LEAD_BULLET);
        items.put("DU_BULLET", Items.DU_BULLET);
        items.put("GOLD_BULLET", Items.GOLD_BULLET);
        items.put("TRINITROBULLETENE_BULLET", Items.TRINITROBULLETENE);
        return items;
    }
}
