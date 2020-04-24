package com.whippyteam.bukkit.tools.scheduler;

import com.whippyteam.api.entity.WhippyPlayer;
import com.whippyteam.api.manager.type.WhippyPlayerManager;
import com.whippyteam.api.storage.Storage;
import com.whippyteam.bukkit.tools.WhippyTools;
import org.bukkit.Bukkit;

public class DataSaveScheduler implements Runnable {

    private final WhippyTools plugin;
    private final WhippyPlayerManager playerManager;
    private final Storage<WhippyPlayer> playerStorage;

    public DataSaveScheduler(final WhippyTools plugin) {
        this.plugin = plugin;
        this.playerManager = (WhippyPlayerManager) this.plugin.getManager("whippyPlayer");
        this.playerStorage = this.plugin.getStorage(this.plugin.getStorageType().getName(), "whippyPlayer");
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.playerManager.get(player.getUniqueId()).isPresent()) {
                WhippyPlayer customPlayer = this.playerManager.get(player.getUniqueId()).get();
                this.playerStorage.save(customPlayer);
            }
        });
    }
}
