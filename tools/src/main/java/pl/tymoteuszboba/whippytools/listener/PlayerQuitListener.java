package pl.tymoteuszboba.whippytools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.entity.WhippyPlayer;
import pl.tymoteuszboba.whippytools.manager.exception.ManagerException;

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
