package io.github.seggan.slimefunwarfare;

import io.github.mooy1.infinitylib.common.Events;
import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.metrics.bukkit.Metrics;
import io.github.seggan.slimefunwarfare.items.guns.Gun;
import io.github.seggan.slimefunwarfare.items.powersuits.ArmorPiece;
import io.github.seggan.slimefunwarfare.items.powersuits.Module;
import io.github.seggan.slimefunwarfare.items.powersuits.PowerSuit;
import io.github.seggan.slimefunwarfare.listeners.BreakListener;
import io.github.seggan.slimefunwarfare.listeners.BulletListener;
import io.github.seggan.slimefunwarfare.listeners.ChatListener;
import io.github.seggan.slimefunwarfare.listeners.ConcreteListener;
import io.github.seggan.slimefunwarfare.listeners.GrenadeListener;
import io.github.seggan.slimefunwarfare.listeners.HitListener;
import io.github.seggan.slimefunwarfare.listeners.ModuleListener;
import io.github.seggan.slimefunwarfare.listeners.NukeListener;
import io.github.seggan.slimefunwarfare.listeners.PyroListener;
import io.github.seggan.slimefunwarfare.lists.Categories;
import io.github.seggan.slimefunwarfare.lists.Items;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

public class SlimefunWarfare extends AbstractAddon implements Listener {

    private static SlimefunWarfare instance;
    private static final Set<UUID> flying = new HashSet<>();

    private static MethodHandle forceFlightMethod;
    private static Object townyFlightApi;

    public SlimefunWarfare() {
        super("wickidcow", "SF_SlimefunWarfare", "master", "auto-update");
    }

    @Override
    public void enable() {
        instance = this;

        // InfinityLib is shaded into the final JAR. No GuizhanLibPlugin runtime dependency is required.
        new Metrics(this, 9227);

        Events.registerListener(new BulletListener());
        Events.registerListener(new PyroListener());
        Events.registerListener(new GrenadeListener());
        Events.registerListener(new ConcreteListener());
        Events.registerListener(new NukeListener());
        Events.registerListener(new HitListener());
        Events.registerListener(new ModuleListener());
        Events.registerListener(new BreakListener());
        Events.registerListener(new ChatListener());

        Categories.setup(this);

        // ItemsAdder enables before/around Warfare, but its custom content is loaded asynchronously.
        // Delay Warfare's Slimefun item registration until IA's data-ready event so valid custom
        // item IDs do not look missing during startup.
        ItemsAdderIntegration.runWhenReady(this, this::finishEnable);
    }

    private void finishEnable() {
        Setup.setupItems(this);
        Setup.setupMelee(this);
        Setup.setupBullets(this);
        Setup.setupGuns(this);
        Setup.setupExplosives(this);
        Setup.setupSpace(this);
        Setup.setupSuits(this);
        Setup.setupResearches();

        Module.setup(this);

        if (getJavaVersion() < 21) {
            getLogger().warning("SlimefunWarfare Legacy requires Java 21 or newer.");
        }

        setupTownyFlightIntegration();
        setupDynaTechIntegration();
        startAutoshootTask();
        startPowerSuitTask();
        startFlightParticleTask();

        getLogger().info("SlimefunWarfare Legacy compatibility layer enabled.");
    }

    @Override
    protected void disable() {
        flying.clear();
        forceFlightMethod = null;
        townyFlightApi = null;
        instance = null;
    }

    private void setupTownyFlightIntegration() {
        if (!getServer().getPluginManager().isPluginEnabled("TownyFlight")) {
            return;
        }

        try {
            Class<?> clazz = Class.forName("com.gmail.llmdlio.townyflight.TownyFlightAPI");
            Method getInstance = clazz.getDeclaredMethod("getInstance");
            getInstance.setAccessible(true);
            townyFlightApi = getInstance.invoke(null);

            MethodHandles.Lookup lookup = MethodHandles.publicLookup();
            MethodType type = MethodType.methodType(void.class, Player.class, boolean.class);
            forceFlightMethod = lookup.findVirtual(clazz, "setForceAllowFlight", type);
            getLogger().info("TownyFlight integration enabled.");
        } catch (ReflectiveOperationException ex) {
            forceFlightMethod = null;
            townyFlightApi = null;
            getLogger().log(Level.WARNING, "TownyFlight was detected but its compatibility API could not be loaded.", ex);
        }
    }

    private void setupDynaTechIntegration() {
        if (!getServer().getPluginManager().isPluginEnabled("DynaTech")) {
            return;
        }

        if (!Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17)) {
            return;
        }

        try {
            Class<?> orechid = resolveDynaTechOrechidClass();
            Method method = orechid.getDeclaredMethod(
                "registerOre", Material.class, SlimefunItemStack.class, float.class
            );
            method.setAccessible(true);

            int configuredChance = getConfig().getInt("space.segganesson-chance", 25);
            int segganessonChance = Math.max(0, Math.min(100, configuredChance));

            method.invoke(
                null,
                Material.WAXED_WEATHERED_CUT_COPPER_STAIRS,
                Items.OSMIUM_METEOR,
                (float) (100 - segganessonChance)
            );
            method.invoke(
                null,
                Material.WAXED_WEATHERED_CUT_COPPER_STAIRS,
                Items.SEGGANESSON_METEOR,
                (float) segganessonChance
            );
            getLogger().info("DynaTech Orechid integration enabled.");
        } catch (ReflectiveOperationException ex) {
            getLogger().log(Level.WARNING, "DynaTech was detected but its Orechid API could not be loaded.", ex);
        }
    }

    private static Class<?> resolveDynaTechOrechidClass() throws ClassNotFoundException {
        try {
            return Class.forName("me.profelements.dynatech.items.electric.machines.Orechid");
        } catch (ClassNotFoundException modernApiMissing) {
            return Class.forName("me.profelements.dynatech.items.tools.Orechid");
        }
    }

    private void startAutoshootTask() {
        if (!getConfig().getBoolean("guns.autoshoot", true)) {
            return;
        }

        // Player inventory access, item mutation and projectile firing must stay on the server thread.
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                if (!player.isSneaking() || player.isFlying()) {
                    continue;
                }

                ItemStack stack = player.getInventory().getItemInMainHand();
                SlimefunItem item = SlimefunItem.getByItem(stack);
                if (!(item instanceof Gun gun)) {
                    continue;
                }

                ItemMeta meta = stack.getItemMeta();
                if (meta == null) {
                    continue;
                }

                PersistentDataContainer container = meta.getPersistentDataContainer();
                long lastUse = container.getOrDefault(Gun.LAST_USE, PersistentDataType.LONG, 0L);
                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastUse) < gun.getCooldown()) {
                    continue;
                }

                container.set(Gun.LAST_USE, PersistentDataType.LONG, currentTime);
                stack.setItemMeta(meta);
                gun.shoot(player, stack);
            }
        }, 1L, 1L);
    }

    private void startPowerSuitTask() {
        // Inventory, flight and potion APIs are not safe from asynchronous tasks.
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                PlayerInventory inventory = player.getInventory();

                ItemStack head = inventory.getHelmet();
                Util.ifPowerSuit(head, suit -> process(head, PowerSuit.getModules(head), suit, player));

                ItemStack chest = inventory.getChestplate();
                Util.ifPowerSuit(chest, suit -> process(chest, PowerSuit.getModules(chest), suit, player));

                ItemStack legs = inventory.getLeggings();
                Util.ifPowerSuit(legs, suit -> process(legs, PowerSuit.getModules(legs), suit, player));

                ItemStack boots = inventory.getBoots();
                Util.ifPowerSuit(
                    boots,
                    suit -> process(boots, PowerSuit.getModules(boots), suit, player),
                    () -> disableSuitFlight(player)
                );
            }
        }, 1L, 20L);
    }

    private void startFlightParticleTask() {
        if (!getConfig().getBoolean("suits.flight-particles", true)) {
            return;
        }

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (UUID uuid : Set.copyOf(flying)) {
                Player player = getServer().getPlayer(uuid);
                if (player == null) {
                    flying.remove(uuid);
                    continue;
                }

                if (player.isFlying()) {
                    player.getWorld().spawnParticle(
                        Particle.SOUL_FIRE_FLAME,
                        player.getLocation().subtract(0, 1, 0),
                        20,
                        0.5,
                        0.5,
                        0.5
                    );
                }
            }
        }, 4L, 4L);
    }

    @EventHandler
    public void onPlayerJoin(@Nonnull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack boots = player.getInventory().getBoots();
        if (player.getAllowFlight()
            && SlimefunItem.getByItem(boots) instanceof PowerSuit
            && containsModule(PowerSuit.getModules(boots), Module.MINI_JETS)) {
            flying.add(player.getUniqueId());
            setForceAllowFlight(player, true);
        }
    }

    @EventHandler
    public void onPlayerLeave(@Nonnull PlayerQuitEvent event) {
        flying.remove(event.getPlayer().getUniqueId());
    }

    private static void process(ItemStack stack, Module[] modules, PowerSuit suit, Player player) {
        UUID uuid = player.getUniqueId();

        for (Module module : modules) {
            PotionEffect effect = module.getEffect();
            if (effect != null && suit.getItemCharge(stack) >= module.getPower()) {
                player.addPotionEffect(effect);
                suit.removeItemCharge(stack, module.getPower());
            }

            switch (module) {
                case MINI_JETS:
                    if (!player.getAllowFlight()) {
                        player.setAllowFlight(true);
                        flying.add(uuid);
                        setForceAllowFlight(player, true);
                    }
                    if (player.isFlying()) {
                        if (suit.getItemCharge(stack) < module.getPower()) {
                            player.setAllowFlight(false);
                            flying.remove(uuid);
                            setForceAllowFlight(player, false);
                        } else {
                            suit.removeItemCharge(stack, module.getPower());
                        }
                    }
                    break;
                case AUXILIARY_GENERATOR:
                    suit.addItemCharge(stack, module.getPower());
                    break;
                default:
                    break;
            }
        }

        if (suit.getType() == ArmorPiece.FEET
            && flying.contains(uuid)
            && !containsModule(modules, Module.MINI_JETS)) {
            player.setAllowFlight(false);
            flying.remove(uuid);
            setForceAllowFlight(player, false);
        }

        suit.addItemCharge(stack, 5);
    }

    private static boolean containsModule(Module[] modules, Module target) {
        if (modules == null) {
            return false;
        }

        for (Module module : modules) {
            if (module == target) {
                return true;
            }
        }
        return false;
    }

    private static void disableSuitFlight(Player player) {
        UUID uuid = player.getUniqueId();
        if (!flying.remove(uuid)) {
            return;
        }

        player.setAllowFlight(false);
        setForceAllowFlight(player, false);
    }

    private static int getJavaVersion() {
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf('.');
            if (dot != -1) {
                version = version.substring(0, dot);
            }
        }
        return Integer.parseInt(version);
    }

    private static void setForceAllowFlight(Player player, boolean allow) {
        if (forceFlightMethod == null || townyFlightApi == null) {
            return;
        }

        try {
            forceFlightMethod.invoke(townyFlightApi, player, allow);
        } catch (Throwable throwable) {
            if (instance != null) {
                instance.getLogger().log(Level.WARNING, "TownyFlight integration call failed.", throwable);
            }
        }
    }

    public static SlimefunWarfare inst() {
        return instance;
    }
}
