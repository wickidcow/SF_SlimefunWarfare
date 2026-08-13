package io.github.seggan.slimefunwarfare;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Central safety gate for destructive SlimefunWarfare gameplay.
 *
 * <p>The allow-list is intentionally default-deny. If the configured world list is missing or blank,
 * only {@code survival_resource} is allowed. This keeps high-impact Warfare effects away from normal
 * survival/Towny worlds unless a server owner explicitly opts another world in.</p>
 */
public final class WorldRestrictions {

    private static final String DEFAULT_ALLOWED_WORLD = "survival_resource";
    private static final String DEFAULT_DENIED_MESSAGE =
        "&cWarfare items are not allowed in this world. &7Please ask your server owner to allow this world if you believe this is a mistake.";

    private WorldRestrictions() {}

    public static boolean isAllowed(World world) {
        if (world == null) {
            return false;
        }

        if (!SlimefunWarfare.inst().getConfig().getBoolean("world-restrictions.enabled", true)) {
            return true;
        }

        String configured = SlimefunWarfare.inst().getConfig().getString("world-restrictions.allowed-worlds");
        if (configured == null || configured.isBlank()) {
            return world.getName().equalsIgnoreCase(DEFAULT_ALLOWED_WORLD);
        }

        for (String entry : configured.split(",")) {
            if (world.getName().equalsIgnoreCase(entry.trim())) {
                return true;
            }
        }

        return false;
    }

    public static boolean check(Player player, Location location) {
        if (location != null && isAllowed(location.getWorld())) {
            return true;
        }

        sendDenied(player);
        return false;
    }

    public static void sendDenied(Player player) {
        if (player == null) {
            return;
        }

        String message = SlimefunWarfare.inst().getConfig().getString("world-restrictions.denied-message");
        if (message == null || message.isBlank()) {
            message = DEFAULT_DENIED_MESSAGE;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
