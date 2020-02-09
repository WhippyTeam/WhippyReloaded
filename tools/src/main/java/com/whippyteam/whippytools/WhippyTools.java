package com.whippyteam.whippytools;

import com.google.common.io.Files;
import com.whippyteam.whippytools.entity.WhippyPlayer;
import com.whippyteam.whippytools.listener.PlayerJoinListener;
import com.whippyteam.whippytools.listener.PlayerQuitListener;
import com.whippyteam.whippytools.manager.WhippyPlayerManager;
import com.whippyteam.whippytools.scheduler.DataSaveScheduler;
import com.whippyteam.whippytools.storage.database.SqlHikariStorage;
import com.whippyteam.whippytools.storage.database.transaction.WhippyPlayerTransactor;
import com.whippyteam.whippytools.storage.exception.TransactionException;
import com.zaxxer.hikari.HikariConfig;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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

        this.registerOnlinePlayers();
        this.registerSchedulers();
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

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerOnlinePlayers() {
        if (Bukkit.getOnlinePlayers().size() == 0) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            WhippyPlayer whippyPlayer = new WhippyPlayer(player.getUniqueId());
            this.getPlayerManager().add(whippyPlayer);
            try {
                this.getPlayerTransactor().load(whippyPlayer);
            } catch (TransactionException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void registerSchedulers() {
        if (this.getConfig().getBoolean("data-saving-cycle", true)) {
            int dataSavingTime = this.getConfig().getInt("data-saving-time", 10);
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DataSaveScheduler(this),
                0, dataSavingTime * 20 * 60);
        }
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

}
