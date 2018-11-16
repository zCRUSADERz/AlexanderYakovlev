package ru.job4j.tracker;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * База данных PostgreSQL.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 29.06.2018
 */
public class PostgresDB implements AutoCloseable {
    private final Properties props = new Properties();
    private Connection connection;
    private boolean connected = false;

    /**
     * Return Connection with database.
     * @return connection with database.
     * @throws DBException if Database access error.
     */
    public Connection getConnection() throws DBException {
        if (isDataBaseExist()) {
            dbConnect();
        } else {
            createDB();
        }
        return this.connection;
    }

    @Override
    public void close() throws DBException {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new DBException("Database access error. Close error.", e);
        }
    }

    private boolean isDataBaseExist() throws DBException {
        boolean result = false;
        localhostConnect();
        try (PreparedStatement statement =
                     this.connection.prepareStatement(
                             "SELECT * FROM pg_database WHERE datname = ?;")) {
            statement.setString(1, this.props.getProperty("database"));
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    result = true;
                }
            } catch (SQLException e) {
                throw  new DBException("Select all databases error.", e);
            }
        } catch (SQLException e) {
            throw new DBException("Database access error.", e);
        }
        return result;
    }

    private void loadProperties() throws DBException {
        if (props.isEmpty()) {
            try (InputStream in = Tracker.class.getResourceAsStream(
                    "/tracker/config.properties")) {
                props.load(in);
            } catch (IOException e) {
                throw new DBException("Properties load error.", e);
            }
        }
    }

    private void createDB() throws DBException {
        localhostConnect();
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(String.format(
                    "CREATE DATABASE %s;", this.props.getProperty("database")
                    )
            );
        } catch (SQLException e) {
            throw new DBException("Create database error", e);
        }
        dbConnect();
        createTable();
    }

    private void createTable() throws DBException {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE items ("
                            + "id serial PRIMARY KEY, "
                            + "name character varying(50) NOT NULL,"
                            + "description text,"
                            + "created timestamp NOT NULL DEFAULT now());"
            );
        } catch (SQLException e) {
            throw new DBException("Create table error", e);
        }
    }

    private void localhostConnect() throws DBException {
        if (this.connected) {
            close();
        }
        loadProperties();
        String url = String.format("jdbc:postgresql://%s:%s/",
                this.props.getProperty("host"), this.props.getProperty("port")
        );
        connect(url);
    }

    private void dbConnect() throws DBException {
        if (this.connected) {
            close();
        }
        loadProperties();
        String url = String.format("jdbc:postgresql://%s:%s/%s",
                this.props.getProperty("host"),
                this.props.getProperty("port"),
                this.props.getProperty("database")
        );
        connect(url);
    }

    private void connect(String url) throws DBException {
        try {
            this.connection = DriverManager.getConnection(url, this.props);
        } catch (SQLException e) {
            throw new DBException("Get connection error.", e);
        }
    }
}
