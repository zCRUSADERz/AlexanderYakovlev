package ru.job4j;

import ru.job4j.model.User;

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
     * @return Optional with user, if added, or empty.
     */
    Optional<User> add(String name, String login, String email);

    /**
     * Update user name.
     * @param id user id.
     * @param name user name.
     * @return Optional with user, if updated, or empty.
     */
    Optional<User> update(long id, String name);

    /**
     * Delete user with id.
     * @param id user id.
     * @return true, if deleted.
     */
    boolean delete(long id);

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
