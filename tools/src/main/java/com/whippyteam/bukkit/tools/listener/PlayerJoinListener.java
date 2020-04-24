package com.whippyteam.bukkit.tools.listener;

import com.whippyteam.api.entity.WhippyPlayer;
import com.whippyteam.api.manager.type.WhippyPlayerManager;
import com.whippyteam.api.storage.Storage;
import com.whippyteam.api.storage.exception.ReadException;
import com.whippyteam.bukkit.tools.WhippyTools;
import com.whippyteam.bukkit.tools.entity.WhippyPlayerImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final WhippyTools plugin;
    private final WhippyPlayerManager playerManager;
    private final Storage<WhippyPlayer> playerStorage;

    public PlayerJoinListener(final WhippyTools plugin) {
        this.plugin = plugin;
        this.playerManager = (WhippyPlayerManager) this.plugin.getManager("whippyPlayer");
        this.playerStorage = this.plugin.getStorage(this.plugin.getStorageType().getName(), "whippyPlayer");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WhippyPlayer customPlayer = this.playerManager.get(player.getUniqueId()).orElseGet(() -> {
            WhippyPlayer newPlayer = new WhippyPlayerImpl(player.getUniqueId());
            this.playerManager.add(newPlayer);
            return newPlayer;
            });

        customPlayer.updatePlayerReference();

        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                this.playerStorage.load(customPlayer);
            } catch (ReadException exception) {
                exception.printStackTrace();
            }
        });
    }

}
