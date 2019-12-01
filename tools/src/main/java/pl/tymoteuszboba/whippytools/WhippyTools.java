package pl.tymoteuszboba.whippytools;

import java.io.File;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import pl.tymoteuszboba.whippytools.command.system.BukkitCommands;
import pl.tymoteuszboba.whippytools.command.system.Commands;
import pl.tymoteuszboba.whippytools.config.ToolsConfiguration;

public class WhippyTools extends JavaPlugin {

    private ToolsConfiguration configuration;

    @Override
    public void onEnable() {
        this.configuration = new ToolsConfiguration(this);
    }

    public ToolsConfiguration getWhippyConfig() {
        return this.configuration;
    }

    private void registerCommands(Object... objects) {
        Commands command = new BukkitCommands(this);
        command.registerCommandObjects(objects);
    }

    //Only for testing purposes.
    protected WhippyTools(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

}
