/*
 * Copyright (C) 2012 BangL <henno.rickowski@googlemail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.bangl.wgdepf.listener;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
 * @author BangL <henno.rickowski@googlemail.com>
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

        Block block = event.getClickedBlock();
        if (block != null) {
            Action action = event.getAction();
            if (block.getType() == Material.DRAGON_EGG
                    && (action == Action.RIGHT_CLICK_BLOCK
                    || action == Action.LEFT_CLICK_BLOCK)) {

                Player player = event.getPlayer();
                WorldGuardPlugin wgp = plugin.getWGP();
                Boolean allows = wgp.getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation()).allows(FLAG_DRAGON_EGG_PORT);
                
                // Block if the action is
                // in a denied region
                // and the action was a right or left click
                // on a dragon egg
                // and the player was no op
                // and has no permission to build here.
                if (!allows
                        && !player.isOp()
                        && !wgp.canBuild(player, block)) {
                    final String msg = this.plugin.getConfig().getString("messages.blocked");
                    player.sendMessage(ChatColor.RED + msg);
                    event.setCancelled(true);
                }
            }
        }
    }
}
