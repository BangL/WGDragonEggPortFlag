package de.bangl.wgdepf.listener;

import com.sk89q.worldguard.protection.flags.StateFlag;
import de.bangl.wgdepf.WGDragonEggPortFlagPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author BangL
 */
public class PlayerInteractListener implements Listener {
    private WGDragonEggPortFlagPlugin plugin;

    // Command flags
    public static final StateFlag FLAG_DRAGON_EGG_PORT = new StateFlag("dragoneggport", true);

    public PlayerInteractListener(WGDragonEggPortFlagPlugin plugin) {
        this.plugin = plugin;

        // Register custom flags
        plugin.getWGCFP().addCustomFlag(FLAG_DRAGON_EGG_PORT);

        // Register events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {

        // Only handle if the action was a click on a dragon egg
        if (((event.getAction() != Action.RIGHT_CLICK_BLOCK)
                && (event.getAction() != Action.LEFT_CLICK_BLOCK))
                || (event.getClickedBlock().getType() != Material.DRAGON_EGG)) {
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        // Is blocked?
        if (!plugin.getWGP().getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation()).allows(FLAG_DRAGON_EGG_PORT)
            || !plugin.getWGP().canBuild(player, block)) {
            String msg = this.plugin.getConfig().getString("messages.blocked");
            player.sendMessage(ChatColor.RED + msg);
            event.setCancelled(true);
        }
    }
}
