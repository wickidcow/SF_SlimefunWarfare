package io.github.seggan.slimefunwarfare.items;

import io.github.seggan.slimefunwarfare.SlimefunWarfare;
import io.github.seggan.slimefunwarfare.lists.Categories;
import io.github.seggan.slimefunwarfare.lists.Items;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Dummy extends SlimefunItem {

    public static final NamespacedKey KEY = new NamespacedKey(SlimefunWarfare.inst(), "dummy");

    public Dummy() {
        super(Categories.GENERAL, Items.DUMMY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            new ItemStack(Material.LEATHER_CHESTPLATE), null, null,
            new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.ARMOR_STAND), null,
            new ItemStack(Material.LEATHER_BOOTS), null, null
        });

        addItemHandler((ItemUseHandler) e -> {
            e.cancel();

            if (e.getClickedBlock().isEmpty()) {
                return;
            }

            Location location = e.getClickedBlock().get().getRelative(e.getClickedFace()).getLocation();
            Husk dummy = location.getWorld().spawn(location, Husk.class);
            PersistentDataAPI.setString(dummy, KEY, "DUMMY");

            dummy.setCustomName("Training Dummy");
            dummy.setCustomNameVisible(true);
            dummy.setRemoveWhenFarAway(false);
            dummy.setAI(false);
            dummy.setAware(false);

            setBaseAttribute(dummy, Attribute.MAX_HEALTH, 1024);
            dummy.setHealth(1024);
            setBaseAttribute(dummy, Attribute.ARMOR, 0);
            setBaseAttribute(dummy, Attribute.ARMOR_TOUGHNESS, 0);

            Player player = e.getPlayer();
            if (player.getGameMode() != GameMode.CREATIVE) {
                ItemUtils.consumeItem(player.getInventory().getItem(e.getHand()), true);
            }
        });
    }

    private static void setBaseAttribute(Husk dummy, Attribute attribute, double value) {
        AttributeInstance instance = dummy.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }
}
