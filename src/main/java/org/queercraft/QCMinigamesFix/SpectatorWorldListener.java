package org.queercraft.QCMinigamesFix;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Blocks the vanilla spectator menu (F key target list) from teleporting a
 * spectator into a different world, but only when the spectator is
 * currently in one of the worlds listed under {@code restricted-worlds} in
 * config.yml. Blue Arcade puts dead players into spectator mode inside the
 * minigame world, but vanilla lets them cycle through every spectator-visible
 * player server-wide, including ones in unrelated worlds (e.g. the main
 * survival world).
 */
public final class SpectatorWorldListener implements Listener {

    private final JavaPlugin plugin;

    public SpectatorWorldListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpectatorTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.SPECTATE) {
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();
        if (to == null || from.getWorld() == null || to.getWorld() == null) {
            return;
        }

        if (from.getWorld().equals(to.getWorld())) {
            return;
        }

        if (!plugin.getConfig().getBoolean("enabled", true)) {
            return;
        }

        if (plugin.getConfig().getStringList("restricted-worlds").contains(from.getWorld().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        if (!plugin.getConfig().getBoolean("enabled", true)) {
            return;
        }

        Player player = event.getPlayer();
        if (plugin.getConfig().getStringList("restricted-worlds").contains(player.getWorld().getName())) {
            // Cancel any outstanding /tpa request the player sent before
            // entering the restricted world, so it can't be accepted mid-game
            // to pull them back out (or pull the accepting party in).
            player.performCommand("tpacancel");
        }
    }
}
