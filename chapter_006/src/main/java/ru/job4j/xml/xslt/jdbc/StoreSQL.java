package ru.job4j.xml.xslt.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * SQLLite БД для хранения записей Entry.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.07.2018
 */
public class StoreSQL {
    private final static String URL_KEY = "jdbc.url";
    private final static String TABLE_NAME_KEY = "jdbc.table_name";
    private final Properties config;
    private int numEntries;

    public StoreSQL(final Properties config) {
        this.config = config;
        this.numEntries = 0;
    }

    /**
     * Генерирует n записсей в БД.
     * @param n количество сгенерированных записей.
     * @throws DBException если выявлена ошибка БД.
     */
    public void generate(int n) throws DBException {
        try (Connection conn = connect()) {
            if (!checkTable(conn)) {
                createTable(conn);
            } else {
                deleteEntries(conn);
            }
            conn.setAutoCommit(false);
            String insertEntry = String.format(
                    "INSERT INTO %s VALUES (?);",
                    tableName()
            );
            try (PreparedStatement st = conn.prepareStatement(insertEntry)) {

                for (int i = 1; i <= n; i++) {
                    st.setInt(1, i);
                    st.execute();
                }
                conn.commit();
                this.numEntries = n;
            } catch (SQLException e) {
                conn.rollback();
                throw new DBException("Ошибка при добавлении сгенерированных записей в БД.", e);
            }
        } catch (SQLException e) {
            throw new DBException("Ошибка закрытия ресурса соединения с БД.", e);
        }
    }

    /**
     * Получить все записи из БД.
     * @return все записи Entry из БД.
     * @throws DBException если выявлена ошибка БД.
     */
    public Entries allEntry() throws DBException {
        List<Entry> entries = new ArrayList<>(this.numEntries);
        Entries result = new Entries(entries);
        if (this.numEntries != 0) {
            try (Connection conn = connect()) {
                String selectAll = String.format(
                        "SELECT field FROM %s;",
                        tableName()
                );
                try (Statement st = conn.createStatement()) {
                    ResultSet resultSet = st.executeQuery(selectAll);
                    while (resultSet.next()) {
                        entries.add(new Entry(resultSet.getInt(1)));
                    }
                } catch (SQLException e) {
                    throw new DBException("Ошибка при выборе всех записей из БД.", e);
                }
            } catch (SQLException e) {
                throw new DBException("Ошибка закрытия ресурса соединения с БД.", e);
            }
        }
        return result;
    }

    private Connection connect() throws DBException {
        Connection result;
        try {
            result = DriverManager.getConnection(this.config.getProperty(URL_KEY));
        } catch (SQLException e) {
            throw new DBException("Не удалось установить соединение с БД.", e);
        }
        return result;
    }

    private boolean checkTable(Connection conn) throws DBException {
        boolean tableExist = false;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rsSet = metaData.getTables(
                    null, null,
                    tableName(), null)) {
                if (rsSet.next()) {
                    tableExist = true;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Не удалось получить метаданные БД.", e);
        }
        return tableExist;
    }

    private void createTable(Connection conn) throws DBException {
        String createTable = String.format(
                "CREATE TABLE %s (field INTEGER);",
                tableName()
        );
        try (Statement st = conn.createStatement()) {
            st.execute(createTable);
        } catch (SQLException e) {
            throw new DBException("Не удалось создать таблицу в БД.", e);
        }
    }

    private void deleteEntries(Connection conn) throws DBException {
        String deleteEntries = String.format(
                "DELETE FROM %s;",
                tableName()
        );
        try (Statement st = conn.createStatement()) {
            st.execute(deleteEntries);
        } catch (SQLException e) {
            throw new DBException("Не удалось удалить записи в таблице.", e);
        }
    }

    private String tableName() {
        return this.config.getProperty(TABLE_NAME_KEY);
    }
}
