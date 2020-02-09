package com.whippyteam.whippytools.listener;

import com.whippyteam.whippytools.WhippyTools;
import com.whippyteam.whippytools.entity.WhippyPlayer;
import com.whippyteam.whippytools.storage.exception.TransactionException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final WhippyTools plugin;

    public PlayerJoinListener(final WhippyTools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WhippyPlayer customPlayer = this.plugin.getPlayerManager().get(player.getUniqueId()).orElseGet(() -> {
            WhippyPlayer newPlayer = new WhippyPlayer(player.getUniqueId());
            this.plugin.getPlayerManager().add(newPlayer);
            return newPlayer;
            });

        customPlayer.updateBukkitPlayer();

        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                this.plugin.getPlayerTransactor().load(customPlayer);
            } catch (TransactionException exception) {
                exception.printStackTrace();
            }
        });
    }

}
