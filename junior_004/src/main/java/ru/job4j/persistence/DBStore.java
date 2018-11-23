package ru.job4j.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.job4j.persistence.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Database store.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.11.2018
 */
public class DBStore implements Store, AutoCloseable {
    private final static DBStore INSTANCE = new DBStore();
    private final BasicDataSource source = new BasicDataSource();
    private final Logger logger = Logger.getLogger(DBStore.class);

    private DBStore() {
        this.source.setDriverClassName("org.postgresql.Driver");
        this.source.setUrl("jdbc:postgresql://localhost:5432/dbstore");
        this.source.setUsername("postgres");
        this.source.setPassword("password");
        this.source.setInitialSize(10);
        this.source.setMinIdle(5);
        this.source.setMaxIdle(10);
        this.source.setMaxOpenPreparedStatements(100);
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    /**
     * Add new User.
     * @param name user name.
     * @param login user login.
     * @param email user email.
     */
    @Override
    public void add(String name, String login, String email) {
        try (Connection connection = this.source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (login, name, email) VALUES (?, ?, ?)")) {
                statement.setString(1, login);
                statement.setString(2, name);
                statement.setString(3, email);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            this.logger.error("SQL error.", ex);
        }
    }

    /**
     * Update user name.
     * @param id user id.
     * @param name user name.
     */
    @Override
    public void update(long id, String name) {
        try (Connection connection = this.source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET name = ? WHERE id = ?")) {
                statement.setString(1, name);
                statement.setLong(2, id);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            this.logger.error("SQL error.", ex);
        }
    }

    /**
     * Delete user with id.
     * @param id user id.
     */
    @Override
    public void delete(long id) {
        try (Connection connection = this.source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            this.logger.error("SQL error.", ex);
        }
    }

    /**
     * Find all users.
     * @return all users.
     */
    @Override
    public Collection<User> findAll() {
        final Collection<User> users = new ArrayList<>();
        try (Connection connection = this.source.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(
                        "SELECT id, login, name, email, created_date FROM users")) {
                    while (resultSet.next()) {
                        users.add(new User(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getTimestamp(5).toLocalDateTime()
                        ));
                    }
                }
            }
        } catch (SQLException ex) {
            this.logger.error("SQL error.", ex);
        }
        return users;
    }

    /**
     * Find user by Id.
     * @param id user id.
     * @return Optional with user, or empty.
     */
    @Override
    public Optional<User> findById(long id) {
        Optional<User> optUser = Optional.empty();
        try (Connection connection = this.source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, login, name, email, created_date FROM users WHERE id=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        optUser = Optional.of(new User(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getTimestamp(5).toLocalDateTime()
                        ));
                    }
                }
            }
        } catch (SQLException ex) {
            this.logger.error("SQL error.", ex);
        }
        return optUser;
    }

    @Override
    public void close() throws Exception {
        this.source.close();
    }
}
