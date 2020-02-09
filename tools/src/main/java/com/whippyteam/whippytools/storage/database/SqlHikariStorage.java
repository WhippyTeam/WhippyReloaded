package com.whippyteam.whippytools.storage.database;

import com.whippyteam.whippytools.storage.database.transaction.TransactionConsumer;
import com.whippyteam.whippytools.storage.exception.StorageException;
import com.whippyteam.whippytools.storage.exception.TransactionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang.Validate;

public class SqlHikariStorage {

    private final HikariDataSource source;

    public SqlHikariStorage(final HikariConfig configuration) {
        Validate.notNull(configuration, "Configuration cannot be null!");
        this.source = new HikariDataSource(configuration);
    }

    public void close() throws StorageException {
        if (this.isAlive()) {
            try {
                this.source.close();
            } catch (Exception ex) {
                throw new StorageException(ex);
            }
        }
    }

    public boolean isAlive() throws StorageException {
        try {
            return this.source.isRunning();
        } catch (Exception ex) {
            throw new StorageException(ex);
        }
    }

    public Connection getConnection() throws TransactionException {
        try {
            return this.source.getConnection();
        } catch (SQLException exception) {
            throw new TransactionException(exception);
        }
    }

    public void update(String query) throws TransactionException {
        Connection connection = this.getConnection();
        try {
            connection.prepareStatement(query).executeUpdate();
        } catch (SQLException exception) {
            throw new TransactionException(exception);
        } finally {
            this.closeConnection(connection);
        }
    }

    public void update(String query, TransactionConsumer consumer) throws TransactionException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            consumer.accept(statement);

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new TransactionException(exception);
        } finally {
            this.closeConnection(connection);
        }
    }

    public ResultSet query(Connection connection, String query) throws TransactionException {
        try {
            return connection.prepareStatement(query).executeQuery();
        } catch (SQLException exception) {
            throw new TransactionException(exception, query);
        }
    }

    public ResultSet query(Connection connection, String query, TransactionConsumer consumer) throws TransactionException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            consumer.accept(statement);

            return statement.executeQuery();
        } catch (SQLException exception) {
            throw new TransactionException(exception, query);
        }
    }

    public void closeConnection(Connection connection) throws TransactionException {
        try {
            connection.close();
        } catch (SQLException exception) {
            throw new TransactionException(exception);
        }
    }
}
