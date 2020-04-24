package com.whippyteam.bukkit.tools.listener;

import com.whippyteam.api.entity.WhippyPlayer;
import com.whippyteam.api.manager.type.WhippyPlayerManager;
import com.whippyteam.api.storage.Storage;
import com.whippyteam.bukkit.tools.WhippyTools;
import com.whippyteam.bukkit.tools.exception.manager.ManagerException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final WhippyTools plugin;
    private final WhippyPlayerManager playerManager;
    private final Storage<WhippyPlayer> playerStorage;


    public PlayerQuitListener(final WhippyTools plugin) {
        this.plugin = plugin;
        this.playerManager = (WhippyPlayerManager) this.plugin.getManager("whippyPlayer");
        this.playerStorage = this.plugin.getStorage(this.plugin.getStorageType().getName(), "whippyPlayer");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        WhippyPlayer customPlayer = this.playerManager.get(player.getUniqueId())
            .orElseThrow(() -> new ManagerException("Player not found!"));

        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () ->
            this.playerStorage.save(customPlayer));
    }

}
