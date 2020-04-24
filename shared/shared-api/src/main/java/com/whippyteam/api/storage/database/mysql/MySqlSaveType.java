package com.whippyteam.api.storage.database.mysql;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.storage.AbstractSaveType;
import com.whippyteam.api.storage.Storage;
import com.whippyteam.api.storage.database.mysql.system.SqlHikariStorage;
import com.zaxxer.hikari.HikariConfig;
import java.util.HashMap;
import java.util.Map;

public class MySqlSaveType extends AbstractSaveType {

    private final ToolsPlugin plugin;
    private final Map<String, Storage> storageMap;

    private SqlHikariStorage database;

    public MySqlSaveType(ToolsPlugin plugin) {
        this.plugin = plugin;
        this.storageMap = new HashMap<>();
    }

    @Override
    public String getName() {
        return "mysql";
    }

    @Override
    public void initialize() {
        PlayerMySqlLoader whippyPlayerSql = new PlayerMySqlLoader(this.plugin, this.database);
        this.storageMap.putIfAbsent("whippyPlayer", whippyPlayerSql);
    }

    @Override
    public void setupEnvironment() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(this.plugin.getWhippyConfig().getString("database.jdbcUrl"));
        hikariConfig.setUsername(this.plugin.getWhippyConfig().getString("database.username"));
        hikariConfig.setPassword(this.plugin.getWhippyConfig().getString("database.password"));
        hikariConfig.setPoolName("WhippyTools-pool");

        this.database = new SqlHikariStorage(hikariConfig);
    }

    @Override
    public Map<String, Storage> getStorageMap() {
        return this.storageMap;
    }
}
