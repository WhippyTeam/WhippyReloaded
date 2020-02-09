package com.whippyteam.whippytools.scheduler;

import com.whippyteam.whippytools.WhippyTools;
import com.whippyteam.whippytools.entity.WhippyPlayer;
import org.bukkit.Bukkit;

public class DataSaveScheduler implements Runnable {

    private final WhippyTools plugin;

    public DataSaveScheduler(final WhippyTools plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            WhippyPlayer customPlayer = this.plugin.getPlayerManager().get(player.getUniqueId()).get();
            this.plugin.getPlayerTransactor().save(customPlayer);
        });
    }
}
