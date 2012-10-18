package de.bangl.wgdepf;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import de.bangl.wgdepf.listener.PlayerInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author BangL
 */
public class WGDragonEggPortFlagPlugin extends JavaPlugin {

    // Plugins
    private WGCustomFlagsPlugin pluginWGCustomFlags;
    private WorldGuardPlugin pluginWorldGuard;

    // Listeners
    private PlayerInteractListener listenerPlayerInteract;

    public WGCustomFlagsPlugin getWGCFP() {
        return pluginWGCustomFlags;
    }

    public WorldGuardPlugin getWGP() {
        return pluginWorldGuard;
    }

    @Override
    public void onEnable() {

        // Load config
        Utils.loadConfig(this);

        // Init WorldGuard
        this.pluginWorldGuard = Utils.getWorldGuard(this);

        // Init and register custom flags
        this.pluginWGCustomFlags = Utils.getWGCustomFlags(this);

        // Register all listeners
        this.listenerPlayerInteract = new PlayerInteractListener(this);
        
    }

    @Override
    public void onDisable() {

        // we nullify all vars, cause it could be a server reload and we don't wanna leave trash in our expensive RAM.
        this.pluginWGCustomFlags = null;
        this.pluginWorldGuard = null;
        this.listenerPlayerInteract = null;

        saveConfig();
    }

}
