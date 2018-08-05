package ru.job4j.sqlru.store;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

/**
 * Sql.ru app database.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuAppDB {
    private final Properties properties;
    private final Logger logger = Logger.getLogger(SqlRuAppDB.class);

    public SqlRuAppDB(final Properties properties) {
        this.properties = properties;
    }

    public Connection getConnection() throws DBException {
        Connection result;
        try {
            Class.forName(this.properties.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            this.logger.fatal("JDBC driver not found.", e);
            throw new DBException("JDBC driver not found.", e);
        }
        try {
            result = DriverManager.getConnection(
                    this.properties.getProperty("jdbc.url"),
                    this.properties.getProperty("jdbc.username"),
                    this.properties.getProperty("jdbc.password")
            );
        } catch (SQLException e) {
            this.logger.fatal("Get connection error.", e);
            throw new DBException("Get connection error.", e);
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
            this.logger.fatal("Close connection error.", e);
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
