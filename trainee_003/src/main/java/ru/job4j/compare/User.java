package ru.job4j.compare;

/**
 * User.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.01.2017
 * @version 1.0
 */
public class User implements Comparable<User> {
    private String name;
    private int age;

    /**
     * @param name - user name.
     * @param age - user age.
     */
    User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String getName() {
        return name;
    }

    int getAge() {
        return age;
    }

    @Override
    public int compareTo(User user) {
        return Integer.compare(this.age, user.age);
    }
}
