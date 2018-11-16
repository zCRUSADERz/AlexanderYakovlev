package ru.job4j.generic.models;

/**
 * User model.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.01.2017
 * @version 1.0
 */
public class User extends Base {
    private static int nextId = 1;

    public User() {
        super(String.valueOf(nextId++));
    }
}
