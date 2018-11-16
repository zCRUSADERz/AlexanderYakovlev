package ru.job4j.map;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * User model.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.03.2018
 */
public class User {
    private final String name;
    private int children;
    private final Calendar birthday;

    public User(String name) {
        this(name, 0, new GregorianCalendar());
    }

    public User(String name, Calendar birthday) {
        this(name, 0, birthday);
    }

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }
}
