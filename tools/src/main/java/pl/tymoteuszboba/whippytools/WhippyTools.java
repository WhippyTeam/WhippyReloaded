package pl.tymoteuszboba.whippytools;

import java.io.File;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import pl.tymoteuszboba.whippytools.command.system.BukkitCommands;
import pl.tymoteuszboba.whippytools.command.system.Commands;
import pl.tymoteuszboba.whippytools.manager.WhippyPlayerManager;
import pl.tymoteuszboba.whippytools.storage.config.ToolsConfiguration;

public class WhippyTools extends JavaPlugin {

    private ToolsConfiguration configuration;
    private WhippyPlayerManager playerManager;

    @Override
    public void onEnable() {
        this.configuration = new ToolsConfiguration(this);
        this.playerManager = new WhippyPlayerManager();
    }

    public ToolsConfiguration getWhippyConfig() {
        return this.configuration;
    }

    private void registerCommands(Object... objects) {
        Commands command = new BukkitCommands(this);
        command.registerCommandObjects(objects);
    }

    public WhippyPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    //Only for testing purposes.
    protected WhippyTools(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

}
