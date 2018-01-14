package ru.job4j;

/**
 * User.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.01.2017
 * @version 1.0
 */
public class User {
    private int id;
    private String name;
    private String city;
    private static int nextId = 1;

    /**
     * Constructor for user.
     * @param name - name.
     * @param city - city.
     */
    User(String name, String city) {
        id = nextId++;
        this.name = name;
        this.city = city;
    }

    int getId() {
        return id;
    }
}
