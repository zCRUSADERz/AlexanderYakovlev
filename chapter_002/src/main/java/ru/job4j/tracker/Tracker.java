package ru.job4j.tracker;

import ru.job4j.tracker.models.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Tracker item.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 */
public class Tracker implements AutoCloseable {
    private final Connection conn;

    public Tracker(final Connection conn) {
        this.conn = conn;
    }

    /**
     * Add item in tracker.
     * @param name item name.
     * @param desc item description.
     * @return item Id.
     * @throws Exception if throw SQLException.
     */
    public int add(String name, String desc) throws Exception {
        return add(name, desc, new Date().getTime());
    }

    /**
     * Add item in tracker.
     * @param name item name.
     * @param desc item description.
     * @param created create date.
     * @return item Id.
     * @throws Exception if throw SQLException.
     */
    public int add(String name, String desc, long created) throws Exception {
        int result;
        String insert =
                "INSERT INTO items (name, description, created) VALUES (?, ?, ?);";
        try (PreparedStatement statement =
                     this.conn.prepareStatement(
                             insert, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, desc);
            statement.setTimestamp(3, new Timestamp(created));
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return result;
    }

    /**
     * Replace Item in tracker by id.
     * @param id id item which replace.
     * @param name new item name.
     * @param desc new item description.
     * @return true, if replaced.
     * @throws Exception if throw SQLException.
     */
    public boolean replace(String id, String name, String desc)
            throws Exception {
        boolean result = false;
        String update = "UPDATE items SET name = ?, description = ? WHERE id = ?;";
        try (PreparedStatement st = this.conn.prepareStatement(update)) {
            st.setString(1, name);
            st.setString(2, desc);
            st.setInt(3, Integer.parseInt(id));
            int execResult = st.executeUpdate();
            if (execResult == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return result;
    }

    /**
     * Delete item by id.
     * @param id - id item.
     * @throws Exception if throw SQLException.
     */
    public void delete(String id) throws Exception {
        String delete = "DELETE FROM items WHERE id = ?;";
        try (PreparedStatement st = this.conn.prepareStatement(delete)) {
            st.setInt(1, Integer.parseInt(id));
            st.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    /**
     * find all items.
     * @return - items in tracker.
     * @throws Exception if throw SQLException.
     */
    public List<Item> findAll() throws Exception {
        List<Item> result = new ArrayList<>();
        try (Statement st = this.conn.createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT * FROM items;")) {
                while (rs.next()) {
                    result.add(new Item(
                            String.valueOf(rs.getInt("id")),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getTimestamp("created").getTime()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return result;
    }

    /**
     * Find items by name.
     * @param key - name.
     * @return - items.
     * @throws Exception if throw SQLException.
     */
    public List<Item> findByName(String key) throws Exception {
        List<Item> result = new ArrayList<>();
        String select = "SELECT * FROM items WHERE name = ?;";
        try (PreparedStatement st = this.conn.prepareStatement(select)) {
            st.setString(1, key);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    result.add(new Item(
                            String.valueOf(rs.getInt("id")),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getTimestamp("created").getTime()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return result;
    }

    /**
     * Find item by id.
     * @param id - id item.
     * @return - item or null if this id is not in tracker items.
     * @throws Exception if throw SQLException.
     */
    public Item findById(String id) throws Exception {
        Item result = null;
        String select = "SELECT * FROM items WHERE id = ?;";
        try (PreparedStatement st = this.conn.prepareStatement(select)) {
            st.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    result = new Item(
                            String.valueOf(rs.getInt("id")),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getTimestamp("created").getTime()
                    );
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return result;
    }

    /**
     * Delete all items in database.
     * @throws Exception if throw SQLException.
     */
    public void deleteAllItems() throws Exception {
        try (Statement st = this.conn.createStatement()) {
            st.execute("DELETE FROM items;");
        }
        try (Statement st = this.conn.createStatement()) {
            st.execute("ALTER SEQUENCE items_id_seq RESTART WITH 1;");
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void close() throws Exception {
        this.conn.close();
    }
}
