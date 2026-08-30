package io.github.seggan.slimefunwarfare;

import io.github.seggan.slimefunwarfare.lists.Items;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Optional ItemsAdder bridge used only to borrow an ItemsAdder item's visual ItemStack.
 *
 * <p>Slimefun remains authoritative for item identity and Warfare remains authoritative
 * for weapon behaviour. No ItemsAdder weapon behaviour is invoked by this class.</p>
 */
public final class ItemsAdderIntegration {

    private static final String CONFIG_ROOT = "itemsadder.visuals";

    private ItemsAdderIntegration() {
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
