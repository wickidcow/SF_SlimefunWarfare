package io.github.seggan.slimefunwarfare.items.guns;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.seggan.slimefunwarfare.SlimefunWarfare;
import io.github.seggan.slimefunwarfare.Util;
import io.github.seggan.slimefunwarfare.WorldRestrictions;
import io.github.seggan.slimefunwarfare.items.Bullet;
import io.github.seggan.slimefunwarfare.lists.Categories;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.DamageableItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

@Getter
public class Gun extends SlimefunItem implements DamageableItem {

    public static final NamespacedKey LAST_USE = AbstractAddon.createKey("last_use");

    private final int range;
    private final int minRange;
    private final int damageDealt;
    private final int cooldown;

    public Gun(SlimefunItemStack item, ItemStack[] recipe, int range, int damage, double cooldown) {
        super(Categories.GUNS, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        this.range = range;
        minRange = 0;
        damageDealt = damage;
        this.cooldown = (int) (cooldown * 1000);

        addItemHandler(getItemHandler());
    }

    public Gun(SlimefunItemStack item, ItemStack[] recipe, int range, int minRange, int damage, double cooldown) {
        super(Categories.GUNS, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        this.range = range;
        this.minRange = SlimefunWarfare.inst().getConfig().getBoolean("guns.min-range-on", true) ? minRange : 0;
        damageDealt = damage;
        this.cooldown = (int) (cooldown * 1000);

        addItemHandler(getItemHandler());
    }

    public ItemUseHandler getItemHandler() {
        return e -> {
            e.cancel();
            Player player = e.getPlayer();
            ItemStack gun = player.getInventory().getItemInMainHand();
            if (!(SlimefunItem.getByItem(gun) instanceof Gun)) {
                return;
            }

            ItemMeta meta = gun.getItemMeta();
            if (meta == null) {
                return;
            }

            PersistentDataContainer container = meta.getPersistentDataContainer();
            long lastUse = container.getOrDefault(Gun.LAST_USE, PersistentDataType.LONG, 0L);
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastUse) < cooldown) {
                player.sendMessage(ChatColor.RED + "Reloading!");
                return;
            }

            container.set(LAST_USE, PersistentDataType.LONG, currentTime);
            gun.setItemMeta(meta);
            shoot(player, gun);
        };
    }

    public void shoot(@Nonnull Player player, @Nonnull ItemStack gun) {
        if (!WorldRestrictions.check(player, player.getLocation())) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        Bullet bullet = checkAndConsume(inventory.getItemInOffHand());
        if (bullet == null && SlimefunWarfare.inst().getConfig().getBoolean("guns.use-bullets-from-inv", true)) {
            bullet = checkAndConsumeStorage(inventory);
        }

        if (bullet == null) {
            player.sendMessage(ChatColor.RED + "Out of bullets!");
            return;
        }

        Vector velocity = player.getEyeLocation().subtract(0, 1, 0).getDirection().multiply(20);
        LlamaSpit spit = player.launchProjectile(LlamaSpit.class);
        spit.setMetadata("isGunBullet", new FixedMetadataValue(SlimefunWarfare.inst(), true));
        spit.setMetadata(
            "damage",
            new FixedMetadataValue(SlimefunWarfare.inst(), this.damageDealt * bullet.getMultiplier())
        );
        spit.setMetadata("isFire", new FixedMetadataValue(SlimefunWarfare.inst(), bullet.isFire()));
        spit.setMetadata(
            "locInfo",
            new FixedMetadataValue(SlimefunWarfare.inst(), Util.serializeLocation(player.getEyeLocation()))
        );
        spit.setMetadata(
            "rangeInfo",
            new FixedMetadataValue(SlimefunWarfare.inst(), range + ":" + minRange)
        );
        spit.setVelocity(velocity);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    /**
     * Checks only normal player storage slots. Slimefun backpacks are intentionally not scanned here.
     *
     * <p>Slimefun Legacy resolves backpack contents asynchronously. Waiting for that result during a weapon
     * interaction would block the server thread, while consuming from the callback would make firing and ammo
     * mutation race each other. Keeping this path synchronous makes each shot atomic and predictable.</p>
     */
    @Nullable
    protected static Bullet checkAndConsumeStorage(@Nonnull PlayerInventory inventory) {
        for (ItemStack stack : inventory.getStorageContents()) {
            if (stack == null) {
                continue;
            }

            Bullet bullet = checkAndConsume(stack);
            if (bullet != null) {
                return bullet;
            }
        }

        return null;
    }

    @Nullable
    protected static Bullet checkAndConsume(@Nonnull ItemStack stack) {
        SlimefunItem item = SlimefunItem.getByItem(stack);
        if (!(item instanceof Bullet bullet)) {
            return null;
        }

        ItemUtils.consumeItem(stack, true);
        return bullet;
    }

    public long getCooldown() {
        return cooldown;
    }
}
