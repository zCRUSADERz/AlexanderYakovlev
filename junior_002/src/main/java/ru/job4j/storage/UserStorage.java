package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Потокобезопасное хранилище пользователей.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.04.2018
 */
@ThreadSafe
public class UserStorage {
    /**
     * Потокобезопасная коллекция. GuardedBy - не требуется.
     */
    private final ConcurrentMap<Integer, User> users;

    public UserStorage() {
        this(new ConcurrentHashMap<>());
    }

    public UserStorage(ConcurrentMap<Integer, User> users) {
        this.users = users;
    }

    /**
     * Добавляет пользователя в хранилище.
     * @param user - пользователь.
     * @return - true, если добавлен.
     */
    public boolean add(User user) {
        boolean result = false;
        User resultUser = users.putIfAbsent(user.id(), user);
        if (resultUser == null) {
            result = true;
        }
        return result;
    }

    /**
     * Обновление пользователя в хранилище.
     * @param user - пользователь.
     * @return - true, если обновлен.
     */
    public boolean update(User user) {
        boolean result = false;
        User resultUser = users.replace(user.id(), user);
        if (resultUser != null) {
            result = true;
        }
        return result;
    }

    /**
     * Удаляет пользователя из хранилища.
     * @param user - пользователь.
     * @return - true, если удален.
     */
    public boolean delete(User user) {
        return users.remove(user.id(), user);
    }

    /**
     * Передает "что-то" от одного пользователя другому в объеме amount.
     * @param fromId - от пользователя с id - fromId.
     * @param toId - пользователю с id - toId
     * @param amount - количество.
     * @return - true, если передано.
     */
    public boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User from = users.get(fromId);
        if (from != null) {
            User to = users.get(toId);
            if (to != null) {
                result = from.transferTo(to, amount);
            }
        }
        return result;
    }
}
