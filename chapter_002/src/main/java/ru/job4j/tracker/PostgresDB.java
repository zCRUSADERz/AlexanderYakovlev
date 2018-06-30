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
     * @throws Exception if throw SQLException or IOException.
     */
    public Connection getConnection() throws Exception {
        try {
            if (isDataBaseExist()) {
                dbConnect();
            } else {
                createDB();
            }
        } catch (IOException | SQLException e) {
            throw new Exception(e);
        }
        return this.connection;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }

    private boolean isDataBaseExist() throws IOException, SQLException {
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
            }
        }
        return result;
    }

    private void loadProperties() throws IOException {
        if (props.isEmpty()) {
            try (InputStream in = Tracker.class.getResourceAsStream(
                    "/tracker/config.properties")) {
                props.load(in);
            }
        }
    }

    private void createDB() throws SQLException, IOException {
        localhostConnect();
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(String.format(
                    "CREATE DATABASE %s;", this.props.getProperty("database")
                    )
            );
        }
        dbConnect();
        createTable();
    }

    private void createTable() throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE items ("
                            + "id serial PRIMARY KEY, "
                            + "name character varying(50) NOT NULL,"
                            + "description text,"
                            + "created timestamp NOT NULL DEFAULT now());"
            );
        }
    }

    private void localhostConnect() throws IOException, SQLException {
        if (this.connected) {
            this.connection.close();
        }
        loadProperties();
        String url = String.format("jdbc:postgresql://%s:%s/",
                this.props.getProperty("host"), this.props.getProperty("port")
        );
        this.connection = DriverManager.getConnection(url, this.props);
    }

    private void dbConnect() throws SQLException, IOException {
        if (this.connected) {
            this.connection.close();
        }
        loadProperties();
        String url = String.format("jdbc:postgresql://%s:%s/%s",
                this.props.getProperty("host"),
                this.props.getProperty("port"),
                this.props.getProperty("database")
        );
        this.connection = DriverManager.getConnection(url, this.props);
    }
}
