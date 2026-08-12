package io.github.seggan.slimefunwarfare.items.powersuits;

import io.github.seggan.slimefunwarfare.SlimefunWarfare;
import io.github.seggan.slimefunwarfare.lists.Categories;
import io.github.seggan.slimefunwarfare.lists.Heads;
import io.github.seggan.slimefunwarfare.lists.Items;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum Module {
    NANOFIBER_CUSHION(0, "Nanofiber Cushion", ArmorPiece.FEET, new ItemStack[]{
        new ItemStack(Material.FEATHER), Items.REINFORCED_SLIMESTEEL, new ItemStack(Material.FEATHER),
        Items.REINFORCED_SLIMESTEEL, Items.MODULE_CASE, Items.REINFORCED_SLIMESTEEL,
        new ItemStack(Material.FEATHER), Items.REINFORCED_SLIMESTEEL, new ItemStack(Material.FEATHER),
    }, 5, "&7Helps absorb impacts", "&7and prevents fall damage"),
    MINI_JETS(1, "Mini Jets", ArmorPiece.FEET, new ItemStack[]{
        Items.OSMIUM_SUPERALLOY, Items.REINFORCED_SLIMESTEEL, Items.OSMIUM_SUPERALLOY,
        SlimefunItems.STEEL_THRUSTER, Items.MODULE_CASE, SlimefunItems.STEEL_THRUSTER,
        Items.OSMIUM_SUPERALLOY, Items.REINFORCED_SLIMESTEEL, Items.OSMIUM_SUPERALLOY
    }, 8, "&7Grants powered flight"),
    LIFE_SUPPORT(2, "Life Support System", PotionEffectType.REGENERATION, 2, ArmorPiece.CHEST, new ItemStack[]{
        SlimefunItems.ESSENCE_OF_AFTERLIFE, Items.SLIMESTEEL, SlimefunItems.ESSENCE_OF_AFTERLIFE,
        Items.SLIMESTEEL, Items.MODULE_CASE, Items.SLIMESTEEL,
        SlimefunItems.ESSENCE_OF_AFTERLIFE, Items.SLIMESTEEL, SlimefunItems.ESSENCE_OF_AFTERLIFE
    }, 5),
    HEAT_SINKS(3, "Heat Sinks", PotionEffectType.FIRE_RESISTANCE, 0, ArmorPiece.CHEST, new ItemStack[]{
        new ItemStack(Material.MAGMA_CREAM), Items.OSMIUM_INGOT, new ItemStack(Material.MAGMA_CREAM),
        Items.OSMIUM_INGOT, Items.MODULE_CASE, Items.OSMIUM_INGOT,
        new ItemStack(Material.MAGMA_CREAM), Items.OSMIUM_INGOT, new ItemStack(Material.MAGMA_CREAM)
    }, 1),
    HYDRAULICS(4, "Integrated Hydraulics", PotionEffectType.STRENGTH, 1, ArmorPiece.CHEST, new ItemStack[]{
        SlimefunItems.REINFORCED_PLATE, Items.OSMIUM_SUPERALLOY, SlimefunItems.REINFORCED_PLATE,
        SlimefunItems.FUEL_BUCKET, Items.MODULE_CASE, SlimefunItems.FUEL_BUCKET,
        SlimefunItems.REINFORCED_PLATE, Items.OSMIUM_SUPERALLOY, SlimefunItems.REINFORCED_PLATE
    }, 3),
    REACTION_WHEELS(5, "Reaction Wheels", ArmorPiece.LEGS, new ItemStack[]{
        SlimefunItems.STEEL_PLATE, Items.OSMIUM_SUPERALLOY, SlimefunItems.STEEL_PLATE,
        SlimefunItems.STEEL_INGOT, Items.MODULE_CASE, SlimefunItems.STEEL_INGOT,
        SlimefunItems.STEEL_PLATE, Items.OSMIUM_SUPERALLOY, SlimefunItems.STEEL_PLATE
    }, 5, "&7Improves movement", "&7and running speed"),
    ENERGY_SHIELD(6, "Personal Energy Shield", PotionEffectType.RESISTANCE, 2, ArmorPiece.CHEST, new ItemStack[]{
        Items.UNPATENTABLIUM, Items.OSMIUM_SUPERALLOY, Items.UNPATENTABLIUM,
        Items.POWER_SUIT_GENERATOR, Items.MODULE_CASE, Items.POWER_SUIT_GENERATOR,
        Items.ENERGY_RECTIFIER, Items.SEGGANESSON, Items.ENERGY_RECTIFIER
    }, 3),
    AQUAMASK(7, "Aquamask", PotionEffectType.CONDUIT_POWER, 0, ArmorPiece.HEAD, new ItemStack[]{
        new ItemStack(Material.PUFFERFISH), SlimefunItems.CLOTH, new ItemStack(Material.PUFFERFISH),
        Items.SLIMESTEEL, Items.MODULE_CASE, Items.SLIMESTEEL,
        new ItemStack(Material.PUFFERFISH), SlimefunItems.CLOTH, new ItemStack(Material.PUFFERFISH)
    }, 2),
    AUXILIARY_GENERATOR(8, "Auxiliary Generator", null, new ItemStack[]{
        Items.OSMIUM_SUPERALLOY, Items.POWER_SUIT_GENERATOR, Items.OSMIUM_SUPERALLOY,
        Items.SEGGANESSON, Items.MODULE_CASE, Items.SEGGANESSON,
        Items.OSMIUM_SUPERALLOY, Items.SEGGANESSON, Items.OSMIUM_SUPERALLOY
    }, 5, "&7Improves power-suit", "&7energy generation"),
    ELECTRONIC_SPRINGS(9, "Electronic Springs", PotionEffectType.JUMP_BOOST, 1, ArmorPiece.LEGS, new ItemStack[]{
        Items.REINFORCED_SLIMESTEEL, SlimefunItems.ADVANCED_CIRCUIT_BOARD, Items.REINFORCED_SLIMESTEEL,
        Items.REINFORCED_SLIMESTEEL, Items.MODULE_CASE, Items.REINFORCED_SLIMESTEEL,
        Items.REINFORCED_SLIMESTEEL, SlimefunItems.BASIC_CIRCUIT_BOARD, Items.REINFORCED_SLIMESTEEL
    }, 3),
    MINI_PISTONS(10, "Mini Pistons", PotionEffectType.HASTE, 3, ArmorPiece.CHEST, new ItemStack[]{
        Items.REINFORCED_SLIMESTEEL, new ItemStack(Material.PISTON), Items.REINFORCED_SLIMESTEEL,
        Items.REINFORCED_SLIMESTEEL, Items.MODULE_CASE, Items.REINFORCED_SLIMESTEEL,
        Items.REINFORCED_SLIMESTEEL, new ItemStack(Material.PISTON), Items.REINFORCED_SLIMESTEEL
    }, 3);

    @Getter
    @Nullable
    private final PotionEffect effect;

    @Getter
    @Nullable
    private final ArmorPiece allowed;

    @Getter
    @Nonnull
    private final SlimefunItemStack item;

    @Nonnull
    private final ItemStack[] recipe;

    @Getter
    private final int power;

    @Getter
    private final int id;

    private static final Map<Integer, Module> cache = new HashMap<>();

    static {
        for (Module module : Module.values()) {
            cache.put(module.id, module);
        }
    }

    Module(
        int id,
        @Nonnull String name,
        @Nullable PotionEffectType effect,
        int level,
        @Nullable ArmorPiece allowed,
        @Nonnull ItemStack[] recipe,
        int power,
        @Nonnull String... lore
    ) {
        this.id = id;
        this.allowed = allowed;
        this.power = power;
        this.effect = effect == null ? null : new PotionEffect(effect, 21, level, false, false, false);
        this.recipe = recipe;

        List<String> loreList = new ArrayList<>(Arrays.asList(lore));
        if (lore.length > 0) {
            loreList.add(0, "");
        }
        loreList.add("");
        if (effect != null) {
            loreList.add(String.format("&7Effect: &a%s %d", getEffectName(effect), level + 1));
        }
        loreList.add(allowed == null ? "&7Installable on any armor piece" : "&7Installable on: " + getArmorPieceName(allowed));
        loreList.add(String.format("&eConsumes %dJ", power));

        this.item = new SlimefunItemStack(
            this.name(),
            PlayerHead.getItemStack(Heads.MODULE),
            "&6" + name,
            loreList.toArray(new String[0])
        );
    }

    Module(
        int id,
        @Nonnull String name,
        ArmorPiece allowed,
        @Nonnull ItemStack[] recipe,
        int power,
        @Nonnull String... lore
    ) {
        this(id, name, null, 0, allowed, recipe, power, lore);
    }

    public static void setup(SlimefunWarfare addon) {
        for (Module module : Module.values()) {
            new ModuleItem(Categories.POWER_SUITS, module.item, RecipeType.ENHANCED_CRAFTING_TABLE, module.recipe, module)
                .register(addon);
        }
    }

    @Nullable
    public static Module getById(int id) {
        return cache.get(id);
    }

    private static String getEffectName(PotionEffectType type) {
        String key = type.getKey().getKey().replace('_', ' ');
        String[] words = key.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(word.substring(0, 1).toUpperCase(Locale.ENGLISH));
            if (word.length() > 1) {
                result.append(word.substring(1).toLowerCase(Locale.ENGLISH));
            }
        }
        return result.toString();
    }

    private static String getArmorPieceName(ArmorPiece piece) {
        return switch (piece) {
            case HEAD -> "Helmet";
            case CHEST -> "Chestplate";
            case LEGS -> "Leggings";
            case FEET -> "Boots";
        };
    }

    public static class ModuleItem extends SlimefunItem implements NotPlaceable {

        @Getter
        private final Module module;

        public ModuleItem(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Module module) {
            super(category, item, recipeType, recipe);
            this.module = module;
        }

        public Module getModule() {
            return module;
        }
    }

    public float getPower() {
        return power;
    }

    public ArmorPiece getAllowed() {
        return allowed;
    }

    public SlimefunItemStack getItem() {
        return item;
    }

    public int getID() {
        return id;
    }

    public PotionEffect getEffect() {
        return effect;
    }
}
