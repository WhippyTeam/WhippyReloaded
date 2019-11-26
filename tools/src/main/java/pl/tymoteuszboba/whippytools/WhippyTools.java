package pl.tymoteuszboba.whippytools;

import org.bukkit.plugin.java.JavaPlugin;
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

}
