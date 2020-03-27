package com.whippyteam.commons.storage.database;

import com.whippyteam.commons.exception.storage.ReadException;
import com.whippyteam.commons.exception.storage.StorageException;
import com.whippyteam.commons.storage.database.transaction.TransactionConsumer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.Validate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Connection getConnection() throws ReadException {
        try {
            return this.source.getConnection();
        } catch (SQLException exception) {
            throw new ReadException(exception);
        }
    }

    public void update(String query) throws ReadException {
        Connection connection = this.getConnection();
        try {
            connection.prepareStatement(query).executeUpdate();
        } catch (SQLException exception) {
            throw new ReadException(exception);
        } finally {
            this.closeConnection(connection);
        }
    }

    public void update(String query, TransactionConsumer consumer) throws ReadException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            consumer.accept(statement);

            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new ReadException(exception);
        } finally {
            this.closeConnection(connection);
        }
    }

    public ResultSet query(Connection connection, String query) throws ReadException {
        try {
            return connection.prepareStatement(query).executeQuery();
        } catch (SQLException exception) {
            throw new ReadException(exception, query);
        }
    }

    public ResultSet query(Connection connection, String query, TransactionConsumer consumer) throws ReadException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            consumer.accept(statement);

            return statement.executeQuery();
        } catch (SQLException exception) {
            throw new ReadException(exception, query);
        }
    }

    public void closeConnection(Connection connection) throws ReadException {
        try {
            connection.close();
        } catch (SQLException exception) {
            throw new ReadException(exception);
        }
    }
}
