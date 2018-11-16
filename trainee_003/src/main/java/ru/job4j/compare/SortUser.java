package ru.job4j.compare;

import java.util.*;

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

    /**
     * Sort list by name length.
     * @param list - sorting list.
     * @return - sorted list.
     */
    List<User> sortNameLength(List<User> list) {
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(
                        o1.getName().length(),
                        o2.getName().length()
                );
            }
        });
        return list;
    }

    /**
     * Sort list by all user fields.
     * @param list - sorting list.
     * @return - sorted list.
     */
    List<User> sortByAllFields(List<User> list) {
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int result = o1.getName().compareTo(o2.getName());
                if (result == 0) {
                    result = Integer.compare(
                            o1.getAge(),
                            o2.getAge()
                    );
                }
                return result;
            }
        });
        return list;
    }
}
