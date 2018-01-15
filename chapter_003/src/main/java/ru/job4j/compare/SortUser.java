package ru.job4j.compare;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Convert List user to sorted set.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.01.2017
 * @version 1.0
 */
public class SortUser {

    /**
     * Sort list to set.
     * @param list - user list.
     * @return - sorted set.
     */
    Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }
}
