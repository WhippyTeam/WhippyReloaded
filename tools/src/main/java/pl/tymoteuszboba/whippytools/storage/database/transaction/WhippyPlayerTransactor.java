package pl.tymoteuszboba.whippytools.storage.database.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.entity.WhippyPlayer;
import pl.tymoteuszboba.whippytools.helper.UniqueIdHelper;
import pl.tymoteuszboba.whippytools.storage.exception.StorageException;
import pl.tymoteuszboba.whippytools.storage.exception.TransactionException;

public class WhippyPlayerTransactor {

    private final WhippyTools plugin;

    private String tableName;

    public WhippyPlayerTransactor(final WhippyTools plugin) {
        this.plugin = plugin;
        this.tableName = this.plugin.getWhippyConfig().getDatabaseSection().getTableName();
    }
    public void load(WhippyPlayer player) throws TransactionException {
        String query = new StringBuilder("SELECT * FROM `")
            .append(tableName)
            .append("` WHERE `uuid` = UNHEX('")
            .append(player.getIdentifier().toString().replace("-", ""))
            .append("');")
            .toString();

        Connection connection = this.plugin.getDatabase().getConnection();

        try {
            ResultSet resultSet = this.plugin.getDatabase().query(connection, query);

            while (resultSet.next()) {
                UUID uniqueId = UniqueIdHelper.getUuidFromBytes(resultSet.getBytes("uuid"));

                player = this.plugin.getPlayerManager().get(uniqueId).orElseGet(() -> {
                    WhippyPlayer newPlayer = new WhippyPlayer(uniqueId);
                    this.plugin.getPlayerManager().add(newPlayer);

                    return newPlayer;
                });
                player.setName(resultSet.getString("name"));
            }

        } catch (SQLException | TransactionException exception) {
            exception.printStackTrace();
        } finally {
            this.plugin.getDatabase().closeConnection(connection);
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
                exception.printStackTrace();
            }
        };

        try {
            this.plugin.getDatabase().update(query, consumer);
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    public void checkTable() {
        String query = new StringBuilder("CREATE TABLE IF NOT EXISTS `")
            .append(tableName)
            .append("` (`uuid` BINARY(16) PRIMARY KEY NOT NULL,")
            .append("`name` VARCHAR(16) NOT NULL);")
            .toString();

        try {
            this.plugin.getDatabase().update(query);
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }
}
