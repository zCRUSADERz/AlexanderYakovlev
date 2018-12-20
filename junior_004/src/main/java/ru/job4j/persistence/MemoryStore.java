package ru.job4j.persistence;


import ru.job4j.persistence.model.User;

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

    private MemoryStore() {
    }

    /**
     * Add new User.
     * Synchronized, потому что необходимо для каждого user
     * поддерживать уникальность поля login
     * @param user added user.
     */
    @Override
    public synchronized void add(User user) {
        final boolean loginExist = this.users
                .values()
                .stream()
                .anyMatch(userInMemory -> userInMemory.loginIsEqual(user.getLogin()));
        if (!loginExist) {
            final long newId = this.idCounter.incrementAndGet();
            final User newUser = new User(
                    newId,
                    user.getLogin(),
                    user.getPassword(),
                    user.getName(),
                    user.getEmail(),
                    LocalDateTime.now()
            );
            this.users.put(newId, newUser);
        }
    }

    /**
     * Update user name.
     * @param user updated user.
     */
    @Override
    public void update(User user) {
        this.users.computeIfPresent(
                user.getId(),
                (aLong, userInMemory) -> {
                    userInMemory.setPassword(user.getPassword());
                    userInMemory.setName(user.getName());
                    userInMemory.setEmail(user.getEmail());
                    return userInMemory;
                }
        );
    }

    /**
     * Delete user with id.
     * @param id user id.
     */
    @Override
    public void delete(long id) {
        this.users.remove(id);
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

    @Override
    public Optional<User> isCredentials(User user) {
        return users.values().stream()
                .filter(userInMemory -> userInMemory.getLogin().equals(user.getLogin())
                        && userInMemory.getPassword().equals(user.getPassword()))
                .findFirst();
    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }
}
