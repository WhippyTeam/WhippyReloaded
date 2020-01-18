package pl.tymoteuszboba.whippytools;

import com.google.common.io.Files;
import com.zaxxer.hikari.HikariConfig;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.tymoteuszboba.whippytools.command.system.BukkitCommands;
import pl.tymoteuszboba.whippytools.command.system.Commands;
import pl.tymoteuszboba.whippytools.listener.PlayerJoinListener;
import pl.tymoteuszboba.whippytools.listener.PlayerQuitListener;
import pl.tymoteuszboba.whippytools.manager.WhippyPlayerManager;
import pl.tymoteuszboba.whippytools.scheduler.DataSaveScheduler;
import pl.tymoteuszboba.whippytools.storage.database.SqlHikariStorage;
import pl.tymoteuszboba.whippytools.storage.database.transaction.WhippyPlayerTransactor;

public class WhippyTools extends JavaPlugin {

    private FileConfiguration messageFile;
    private SqlHikariStorage database;

    private WhippyPlayerManager playerManager;
    private WhippyPlayerTransactor playerTransactor;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        messageFile = this.registerLocaleFile();

        this.playerManager = new WhippyPlayerManager();
        this.database = new SqlHikariStorage(this.loadDatabaseConfiguration());
        this.playerTransactor = new WhippyPlayerTransactor(this);
        this.playerTransactor.checkTable();

        this.registerListeners(new PlayerJoinListener(this),
            new PlayerQuitListener(this));

        this.registerSchedulers();
    }

    public SqlHikariStorage getDatabase() {
        return this.database;
    }

    public FileConfiguration getMessageFile() {
        return this.messageFile;
    }

    public WhippyPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public WhippyPlayerTransactor getPlayerTransactor() {
        return this.playerTransactor;
    }

    private FileConfiguration registerLocaleFile() {
        String locale = this.getConfig().getString("locale", "en");
        File file = new File(this.getDataFolder(), "locale-" + locale + ".yml");

        if (!file.exists()) {
            try {
                Files.createParentDirs(file);
                file.createNewFile();
            } catch (IOException exception) {
                this.getLogger().severe("Error while trying to create locale files!");
                exception.printStackTrace();
            }

            this.saveResource("locale-" + locale + ".yml", true);
        }

        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.options().copyDefaults(true);
        return fileConfiguration;
    }

    private HikariConfig loadDatabaseConfiguration() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(this.getConfig().getString("database.jdbcUrl"));
        hikariConfig.setUsername(this.getConfig().getString("database.username"));
        hikariConfig.setPassword(this.getConfig().getString("database.password"));
        hikariConfig.setPoolName("WhippyTools-pool");
        return hikariConfig;
    }

    private void registerCommands(Object... objects) {
        Commands command = new BukkitCommands(this);
        command.registerCommandObjects(objects);
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerSchedulers() {
        if (this.getConfig().getBoolean("data-saving-cycle", true)) {
            int dataSavingTime = this.getConfig().getInt("data-saving-time", 10);
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DataSaveScheduler(this),
                0, dataSavingTime * 20 * 60);
        }
    }

}
