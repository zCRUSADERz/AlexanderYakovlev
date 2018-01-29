package ru.job4j.generic.models;

/**
 * Base model.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.01.2017
 * @version 1.0
 */
public abstract class Base {
    private final String id;

    Base(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
