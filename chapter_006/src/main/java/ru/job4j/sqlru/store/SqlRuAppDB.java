package ru.job4j.sqlru.store;

import org.apache.log4j.Logger;
import ru.job4j.sqlru.utils.Config;
import ru.job4j.sqlru.utils.ConfigException;

import java.sql.*;
import java.util.Properties;

/**
 * Sql.ru app database.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuAppDB {
    private final Config config;
    private final Logger logger = Logger.getLogger(SqlRuAppDB.class);

    public SqlRuAppDB(final Config config) {
        this.config = config;
    }

    public Connection getConnection() throws DBException {
        Connection result;
        try {
            Properties properties = this.config.getConfig();
            Class.forName(properties.getProperty("jdbc.driver"));
            result = DriverManager.getConnection(
                    properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.username"),
                    properties.getProperty("jdbc.password")
            );
        } catch (ClassNotFoundException e) {
            throw new DBException("JDBC driver not found.", e);
        } catch (SQLException e) {
            throw new DBException("Get connection error.", e);
        } catch (ConfigException e) {
            throw new DBException("Configuration error.", e);
        }
        return result;
    }

    /**
     * Initialize DB structure.
     * @throws DBException if throw SQLException.
     */
    public void initDB() throws DBException {
        try (Connection conn = getConnection()) {
            if (!checkTable(conn)) {
                this.logger.info("Database initialization.");
                createTable(conn);
            }
        } catch (SQLException e) {
            throw new DBException("Close connection error.", e);
        }
    }

    private boolean checkTable(Connection conn) throws DBException {
        boolean tableExist = false;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rsSet = metaData.getTables(
                    null, null,
                    "offers", null)) {
                if (rsSet.next()) {
                    tableExist = true;
                }
            }
        } catch (SQLException e) {
            this.logger.fatal("Get database metadata error.", e);
            throw new DBException("Get database metadata error.", e);
        }
        return tableExist;
    }

    private void createTable(Connection conn) throws DBException {
        String createTable = "CREATE TABLE offers ("
                + "id INTEGER PRIMARY KEY,"
                + "title CHARACTER VARYING,"
                + "description CHARACTER VARYING,"
                + "created DATE,"
                + "url CHARACTER VARYING);";
        try (Statement st = conn.createStatement()) {
            st.execute(createTable);
        } catch (SQLException e) {
            this.logger.fatal("Create table offers error.", e);
            throw new DBException("Create table offers error.", e);
        }
    }
}
