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
     * @param user added user
     */
    @Override
    public void add(User user) {
        final String query = ""
                + "INSERT INTO users (login, password, name, email) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = this.source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getName());
                statement.setString(4, user.getEmail());
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            this.logger.error("SQL error.", ex);
        }
    }

    /**
     * Update user name.
     * @param user updated user.
     */
    @Override
    public void update(User user) {
        final String query = ""
                + "UPDATE users SET password = ?, name = ?, email = ? "
                + "WHERE id = ?";
        try (Connection connection = this.source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getPassword());
                statement.setString(2, user.getName());
                statement.setString(3, user.getEmail());
                statement.setLong(4, user.getId());
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
        final String query = ""
                + "SELECT id, login, password, name, email, created_date "
                + "FROM users";
        final Collection<User> users = new ArrayList<>();
        try (Connection connection = this.source.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        users.add(new User(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getTimestamp(6).toLocalDateTime()
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
        final String query = ""
                + "SELECT id, login, password, name, email, created_date "
                + "FROM users WHERE id=?";
        try (Connection connection = this.source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        optUser = Optional.of(new User(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getTimestamp(6).toLocalDateTime()
                        ));
                    }
                }
            }
        } catch (SQLException ex) {
            this.logger.error("SQL error.", ex);
        }
        return optUser;
    }

    public Optional<User> isCredentials(User user) {
        Optional<User> optUser = Optional.empty();
        final String query = ""
                + "SELECT id, login, password, name, email, created_date "
                + "FROM users WHERE login = ? AND password = ?";
        try (Connection connection = this.source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        optUser = Optional.of(new User(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getTimestamp(6).toLocalDateTime()
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
