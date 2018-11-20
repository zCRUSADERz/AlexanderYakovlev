package ru.job4j;


import ru.job4j.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Store in memory.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 20.11.2018
 */
public class MemoryStore implements Store {
    private final static MemoryStore INSTANCE = new MemoryStore();
    private final ConcurrentMap<Long, User> users = new ConcurrentHashMap<>();
    /**
     * User id counter.
     */
    private final AtomicLong idCounter = new AtomicLong(0);

    /**
     * Add new User.
     * Synchronized, потому что необходимо для каждого user
     * поддерживать уникальность поля login
     * @param name user name.
     * @param login user login.
     * @param email user email.
     * @return Optional with user, if added, or empty.
     */
    @Override
    public synchronized Optional<User> add(String name,
                                           String login,
                                           String email) {
        final Optional<User> optNewUser;
        final Optional<User> userWithSameLogin = this.users
                .values()
                .stream()
                .filter(user -> user.loginIsEqual(login))
                .findFirst();
        if (!userWithSameLogin.isPresent()) {
            final long newId = this.idCounter.incrementAndGet();
            final User newUser = new User(
                    newId,
                    name,
                    login,
                    email,
                    LocalDateTime.now()
            );
            this.users.put(newId, newUser);
            optNewUser = Optional.of(newUser);
        } else {
            optNewUser = Optional.empty();
        }
        return optNewUser;
    }

    /**
     * Update user name.
     * @param id user id.
     * @param name user name.
     * @return Optional with user, if updated, or empty.
     */
    @Override
    public Optional<User> update(long id, String name) {
        return Optional.ofNullable(
                this.users.computeIfPresent(
                        id,
                        (aLong, user) -> user.rename(name)
                )
        );
    }

    /**
     * Delete user with id.
     * @param id user id.
     * @return true, if deleted.
     */
    @Override
    public boolean delete(long id) {
        return Optional.ofNullable(this.users.remove(id)).isPresent();
    }

    /**
     * Find all users.
     * @return all users.
     */
    @Override
    public Collection<User> findAll() {
        return this.users.values();
    }

    /**
     * Find user by Id.
     * @param id user id.
     * @return Optional with user, or empty.
     */
    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(this.users.get(id));
    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }
}
