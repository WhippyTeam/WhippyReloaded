package pl.tymoteuszboba.whippytools;

import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.java.JavaPlugin;
import pl.tymoteuszboba.whippytools.command.system.BukkitCommands;
import pl.tymoteuszboba.whippytools.command.system.Commands;
import pl.tymoteuszboba.whippytools.manager.WhippyPlayerManager;
import pl.tymoteuszboba.whippytools.storage.config.ToolsConfiguration;
import pl.tymoteuszboba.whippytools.storage.database.SqlHikariStorage;
import pl.tymoteuszboba.whippytools.storage.database.transaction.WhippyPlayerTransactor;

public class WhippyTools extends JavaPlugin {

    private ToolsConfiguration configuration;
    private SqlHikariStorage database;

    private WhippyPlayerManager playerManager;
    private WhippyPlayerTransactor playerTransactor;

    @Override
    public void onEnable() {
        this.configuration = new ToolsConfiguration(this);
        this.database = new SqlHikariStorage(this.loadDatabaseConfiguration());

        this.playerManager = new WhippyPlayerManager();
        this.playerTransactor = new WhippyPlayerTransactor(this);
    }

    public ToolsConfiguration getWhippyConfig() {
        return this.configuration;
    }

    public SqlHikariStorage getDatabase() {
        return this.database;
    }


    public WhippyPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public WhippyPlayerTransactor getPlayerTransactor() {
        return this.playerTransactor;
    }

    private HikariConfig loadDatabaseConfiguration() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(this.getWhippyConfig().getDatabaseSection().getJdbcUrl());
        hikariConfig.setUsername(this.getWhippyConfig().getDatabaseSection().getUsername());
        hikariConfig.setPassword(this.getWhippyConfig().getDatabaseSection().getPassword());
        hikariConfig.setPoolName("WhippyTools-pool");
        return hikariConfig;
    }

    private void registerCommands(Object... objects) {
        Commands command = new BukkitCommands(this);
        command.registerCommandObjects(objects);
    }

}
