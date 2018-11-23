package ru.job4j.persistence;

import ru.job4j.persistence.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Store.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.11.2018
 */
public interface Store {

    /**
     * Add new User.
     * @param name user name.
     * @param login user login.
     * @param email user email.
     */
    void add(String login, String name, String email);

    /**
     * Update user name.
     * @param id user id.
     * @param name user name.
     */
    void update(long id, String name);

    /**
     * Delete user with id.
     * @param id user id.
     */
    void delete(long id);

    /**
     * Find all users.
     * @return all users.
     */
    Collection<User> findAll();

    /**
     * Find user by Id.
     * @param id user id.
     * @return Optional with user, or empty.
     */
    Optional<User> findById(long id);
}
