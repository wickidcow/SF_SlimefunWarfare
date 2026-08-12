package io.github.seggan.slimefunwarfare.georesources;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.seggan.slimefunwarfare.Util;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import java.util.Locale;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

public class Monazite implements GEOResource {

    private static final Biome theBiome;

    static {
        String s = "ONFNYG_" + Util.whatIsThis;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'm') c += 13;
            else if (c >= 'A' && c <= 'M') c += 13;
            else if (c >= 'n' && c <= 'z') c -= 13;
            else if (c >= 'N' && c <= 'Z') c -= 13;
            sb.append(c);
        }
        theBiome = Biome.valueOf(sb.toString().toUpperCase(Locale.ENGLISH));
    }

    private final NamespacedKey key;
    private final ItemStack item;

    public Monazite(ItemStack stack) {
        // Keep the historical key spelling for existing worlds/data compatibility.
        key = AbstractAddon.createKey("mozanite");
        item = stack;
    }

    @Override
    public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
        if (biome == theBiome) {
            return hasInfinityExpansion() ? 1 : 4;
        }

        return 0;
    }

    @Override
    public int getMaxDeviation() {
        return hasInfinityExpansion() ? 2 : 3;
    }

    private static boolean hasInfinityExpansion() {
        return Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion")
            || Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion2");
    }

    @Nonnull
    @Override
    public String getName() {
        return "Monazite";
    }

    @Nonnull
    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return key;
    }
}
