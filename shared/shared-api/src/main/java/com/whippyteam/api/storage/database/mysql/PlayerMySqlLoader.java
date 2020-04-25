package com.whippyteam.api.storage.database.mysql;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.entity.WhippyPlayer;
import com.whippyteam.api.helper.EngineHelper;
import com.whippyteam.api.helper.UniqueIdHelper;
import com.whippyteam.api.manager.type.WhippyPlayerManager;
import com.whippyteam.api.storage.Storage;
import com.whippyteam.api.storage.database.mysql.system.SqlHikariStorage;
import com.whippyteam.api.storage.database.mysql.system.transaction.TransactionConsumer;
import com.whippyteam.api.storage.exception.ReadException;
import com.whippyteam.api.storage.exception.StorageException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMySqlLoader implements Storage<WhippyPlayer> {

    private ToolsPlugin plugin;

    private final SqlHikariStorage storage;
    private final WhippyPlayerManager manager;

    private String tableName;

    public PlayerMySqlLoader(ToolsPlugin plugin, SqlHikariStorage storage) {
        this.plugin = plugin;

        this.storage = storage;
        this.manager = (WhippyPlayerManager) plugin.getManager("whippyPlayer");
        this.tableName = plugin.getWhippyConfig().getString("database.tableName", "WhippyTools");
    }

    public void load(WhippyPlayer player) throws ReadException {
        String query = new StringBuilder("SELECT * FROM `")
            .append(tableName)
            .append("` WHERE `uuid` = UNHEX('")
            .append(player.getIdentifier().toString().replace("-", ""))
            .append("');")
            .toString();

        Connection connection = storage.getConnection();

        try {
            ResultSet resultSet = storage.query(connection, query);

            while (resultSet.next()) {
                UUID uniqueId = UniqueIdHelper.getUuidFromBytes(resultSet.getBytes("uuid"));

                player = manager.get(uniqueId).orElseGet(() -> {
                    Map<Class<?>, Object> paramsMap = new HashMap<>();
                    paramsMap.put(UUID.class, uniqueId);
                    WhippyPlayer newPlayer = EngineHelper.initiateObject(plugin, WhippyPlayer.class, "entity.WhippyPlayerImpl", paramsMap);
                    manager.add(newPlayer);

                    return newPlayer;
                });

                player.setName(resultSet.getString("name"));
            }

        } catch (SQLException | ReadException exception) {
            this.plugin.getWhippyLogger().severe("Could not read data from the database!", exception);
        } finally {
            storage.closeConnection(connection);
        }
    }

    public void save(WhippyPlayer player) {
        String query = new StringBuilder("INSERT INTO `")
            .append(tableName)
            .append("` (uuid, name) VALUES")
            .append(" (?, ?) ") //2 Question Marks
            .append("ON DUPLICATE KEY UPDATE ")
            .append("`name` = ?;")
            .toString();

        TransactionConsumer consumer = preparedStatement -> {
            try {
                preparedStatement.setBytes(1, UniqueIdHelper.getBytesFromUuid(player.getIdentifier()));
                preparedStatement.setString(2, player.getName());
                preparedStatement.setString(3, player.getName());
            } catch (SQLException exception) {
                this.plugin.getWhippyLogger().severe("Could not save data to the database!", exception);
            }
        };

        try {
            storage.update(query, consumer);
        } catch (StorageException exception) {
            this.plugin.getWhippyLogger().severe("Could not update data to the database!", exception);
        }
    }

    @Override
    public boolean checkIfCreated() {
        String query = new StringBuilder("CREATE TABLE IF NOT EXISTS `")
            .append(tableName)
            .append("` (`uuid` BINARY(16) PRIMARY KEY NOT NULL,")
            .append("`name` VARCHAR(16) NOT NULL);")
            .toString();

        try {
            storage.update(query);
            return true;
        } catch (StorageException exception) {
            this.plugin.getWhippyLogger().severe("Could not check table structure in the database!", exception);
        }
        return false;
    }
}
