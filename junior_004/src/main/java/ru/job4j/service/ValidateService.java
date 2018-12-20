package ru.job4j.service;

import ru.job4j.persistence.DBStore;
import ru.job4j.persistence.Store;
import ru.job4j.persistence.model.User;
import ru.job4j.persistence.model.UserException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.*;

/**
 * Validate service for users.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.11.2018
 */
public class ValidateService {
    private final static ValidateService INSTANCE = new ValidateService();
    private final Store store = DBStore.getInstance();

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    /**
     * Add new User.
     * @param user added user.
     */
    public void add(User user) throws UserException {
        this.validateLogin(user);
        this.validatePassword(user);
        this.validateName(user);
        this.validateEmail(user);
        this.store.add(user);

    }

    /**
     * Update user name.
     * @param user updated user.
     */
    public void update(User user) throws UserException {
        this.validatePassword(user);
        this.validateName(user);
        this.validateEmail(user);
        this.store.update(user);
    }

    /**
     * Delete user with id.
     * @param id user id.
     */
    public void delete(String id) throws UserException {
        final long userId = validateId(id);
        this.store.delete(userId);
    }

    /**
     * Find all users.
     * @return all users.
     */
    public Collection<User> findAll() {
        return this.store.findAll();
    }

    /**
     * Find user by Id.
     * @param id user id.
     * @return Optional with user, or empty.
     */
    public Optional<User> findById(String id) throws UserException {
        final long userId = validateId(id);
        return this.store.findById(userId);
    }

    public Optional<User> isCredentials(User user) throws UserException {
        this.validateLogin(user);
        this.validatePassword(user);
        return this.store.isCredentials(user);
    }

    private boolean empty(String string) {
        return Objects.isNull(string) || "".equals(string.trim());
    }

    private long validateId(String id) throws UserException {
        final long result;
        try {
            result = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new UserException(
                    String.format(
                            "%s - is not number.",
                            id
                    ),
                    ex
            );
        }
        return result;
    }

    private void validateEmail(User user) throws UserException {
        if (this.empty(user.getEmail())) {
            throw new UserException(String.format("Email is empty. %s", user));
        }
        try {
            new InternetAddress(user.getEmail()).validate();
        } catch (AddressException ex) {
            throw new UserException(String.format("Invalid email address. %s", user), ex);
        }
    }

    private void validateLogin(User user) throws UserException {
        if (this.empty(user.getLogin())) {
            throw new UserException(String.format("Login is empty. %s", user));
        }
    }

    private void validatePassword(User user) throws UserException {
        final int minPasswordLength = 6;
        if (this.empty(user.getPassword())) {
            throw new UserException(String.format("Password is empty. %s", user));
        }
        if (user.getPassword().length() < minPasswordLength) {
            throw new UserException(String.format(
                    "The password is too short: it must be at least %s characters. %s",
                    minPasswordLength, user
            ));
        }
    }

    private void validateName(User user) throws UserException {
        if (this.empty(user.getName())) {
            throw new UserException(String.format("User name is empty. %s", user));
        }
    }
}
