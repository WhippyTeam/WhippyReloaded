package pl.tymoteuszboba.whippytools.scheduler;

import org.bukkit.Bukkit;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.entity.WhippyPlayer;

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
