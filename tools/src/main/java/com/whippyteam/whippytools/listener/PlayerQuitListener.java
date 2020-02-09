package com.whippyteam.whippytools.listener;

import com.whippyteam.whippytools.WhippyTools;
import com.whippyteam.whippytools.entity.WhippyPlayer;
import com.whippyteam.whippytools.manager.exception.ManagerException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final WhippyTools plugin;

    public PlayerQuitListener(final WhippyTools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        WhippyPlayer customPlayer = this.plugin.getPlayerManager().get(player.getUniqueId())
            .orElseThrow(() -> new ManagerException("Player not found!"));

        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () ->
            this.plugin.getPlayerTransactor().save(customPlayer));
    }

}
