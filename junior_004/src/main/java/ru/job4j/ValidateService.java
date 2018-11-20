package ru.job4j;

import ru.job4j.model.User;

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
    private final Store store = MemoryStore.getInstance();

    /**
     * Add new User.
     * @param name user name.
     * @param login user login.
     * @param email user email.
     * @return Iterable with errors message, or result message.
     */
    public Iterable<String> add(String name, String login, String email) {
        final Collection<String> result = new ArrayList<>();
        if (this.stringIsEmpty(name)) {
            result.add(this.notFilled("Name"));
        }
        if (this.stringIsEmpty(login)) {
            result.add(this.notFilled("Login"));
        }
        if (this.stringIsEmpty(email)) {
            result.add(this.notFilled("Email"));
        } else if (!this.emailValid(email)) {
            result.add(String.format("Email: %s, invalid.", email));
        }
        if (result.isEmpty()) {
            final Optional<User> user = this.store.add(name, login, email);
            if (!user.isPresent()) {
                result.add(String.format("Login: %s, already taken.", login));
            } else {
                result.add(String.format("%s created.", user.get()));
            }
        }
        return result;
    }

    /**
     * Update user name.
     * @param id user id.
     * @param name user name.
     * @return Iterable with errors message, or result message.
     */
    public Iterable<String> update(String id, String name) {
        final Collection<String> result = new ArrayList<>();
        this.validateId(id).ifPresent(result::add);
        if (this.stringIsEmpty(name)) {
            result.add(this.notFilled("Name"));
        }
        if (result.isEmpty()) {
            final Optional<User> user = this.store.update(Long.parseLong(id), name);
            if (!user.isPresent()) {
                result.add(String.format("User with id: %s, not found.", id));
            } else {
                result.add(String.format("%s updated.", user.get()));
            }
        }
        return result;
    }

    /**
     * Delete user with id.
     * @param id user id.
     * @return Iterable with errors message, or result message.
     */
    public Iterable<String> delete(String id) {
        final Collection<String> result = new ArrayList<>();
        this.validateId(id).ifPresent(result::add);
        if (result.isEmpty()) {
            if (this.store.delete(Long.parseLong(id))) {
                result.add(String.format("User with id: %s, was removed.", id));
            } else {
                result.add(String.format("User with id: %s, not found.", id));
            }
        }
        return result;
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
        if (!this.stringIsEmpty(id) && this.idValid(id)) {
            result = this.store.findById(Long.parseLong(id));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    private Optional<String> validateId(String id) {
        final Optional<String> error;
        if (this.stringIsEmpty(id)) {
            error = Optional.of(this.notFilled("Id"));
        } else if (!this.idValid(id)) {
            error = Optional.of(String.format("Id: %s, not a number.", id));
        } else {
            error = Optional.empty();
        }
        return error;
    }

    private boolean stringIsEmpty(String string) {
        return Objects.isNull(string) || "".equals(string.trim());
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

    private String notFilled(String parameterName) {
        return String.format("%s - not filled.", parameterName);
    }
}
