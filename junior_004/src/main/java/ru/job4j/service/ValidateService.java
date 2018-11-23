package ru.job4j.service;

import ru.job4j.persistence.DBStore;
import ru.job4j.persistence.Store;
import ru.job4j.persistence.model.User;

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

    /**
     * Add new User.
     * @param name user name.
     * @param login user login.
     * @param email user email.
     */
    public void add(String name, String login, String email) {
        if (this.stringNotEmpty(name)
                && this.stringNotEmpty(login)
                && this.stringNotEmpty(email)
                && this.emailValid(email)) {
            this.store.add(name, login, email);
        }
    }

    /**
     * Update user name.
     * @param id user id.
     * @param name user name.
     */
    public void update(String id, String name) {
        if (this.stringNotEmpty(id)
                && this.stringNotEmpty(name)
                && this.idValid(id)) {
            this.store.update(Long.parseLong(id), name);
        }
    }

    /**
     * Delete user with id.
     * @param id user id.
     */
    public void delete(String id) {
        if (this.stringNotEmpty(id) && this.idValid(id)) {
            this.store.delete(Long.parseLong(id));
        }
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
    public Optional<User> findById(String id) {
        final Optional<User> result;
        if (this.stringNotEmpty(id) && this.idValid(id)) {
            result = this.store.findById(Long.parseLong(id));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    private boolean stringNotEmpty(String string) {
        return !(Objects.isNull(string) || "".equals(string.trim()));
    }

    private boolean idValid(String id) {
        boolean valid = true;
        try {
            long parsed = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            valid = false;
        }
        return valid;
    }

    private boolean emailValid(String email) {
        boolean valid = true;
        try {
            new InternetAddress(email).validate();
        } catch (AddressException ex) {
            valid = false;
        }
        return valid;
    }
}
