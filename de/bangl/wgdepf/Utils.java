package de.bangl.wgdepf;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author BangL
 */
public class Utils {

    public static WGCustomFlagsPlugin getWGCustomFlags(WGDragonEggPortFlagPlugin plugin) {
        Plugin wgcf = plugin.getServer().getPluginManager().getPlugin("WGCustomFlags");
        if (wgcf == null || !(wgcf instanceof WGCustomFlagsPlugin)) {
            return null;
        }
        return (WGCustomFlagsPlugin)wgcf;
    }

    public static WorldGuardPlugin getWorldGuard(WGDragonEggPortFlagPlugin plugin) {
        Plugin wg = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if (wg == null || !(wg instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)wg;
    }

    public static void loadConfig(WGDragonEggPortFlagPlugin plugin) {
        plugin.getConfig().addDefault("messages.blocked", "Can't touch this.");
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }
}
