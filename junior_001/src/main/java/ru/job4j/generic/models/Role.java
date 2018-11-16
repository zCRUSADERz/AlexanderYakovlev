package ru.job4j.generic.models;

/**
 * Role model.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.01.2017
 * @version 1.0
 */
public class Role extends Base {
    private static int nextId = 1;

    public Role() {
        super(String.valueOf(nextId++));
    }
}
