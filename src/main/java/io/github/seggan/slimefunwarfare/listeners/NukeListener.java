package io.github.seggan.slimefunwarfare.listeners;

import io.github.seggan.slimefunwarfare.WorldRestrictions;
import io.github.seggan.slimefunwarfare.items.NuclearBomb;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class NukeListener implements Listener {

    @EventHandler
    public void onExplosiveDispense(BlockDispenseEvent e) {
        if (SlimefunItem.getByItem(e.getItem()) instanceof NuclearBomb) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onNuclearBlockPrime(TNTPrimeEvent e) {
        if (BlockStorage.check(e.getBlock()) instanceof NuclearBomb
            && !WorldRestrictions.isAllowed(e.getBlock().getWorld())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onNuclearEntityPrime(ExplosionPrimeEvent e) {
        if (e.getEntity().hasMetadata("isNuke")
            && !WorldRestrictions.isAllowed(e.getEntity().getWorld())) {
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }
}
