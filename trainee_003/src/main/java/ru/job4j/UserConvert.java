package ru.job4j;

import java.util.HashMap;
import java.util.List;

/**
 * User convert to Map.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.01.2017
 * @version 1.0
 */
public class UserConvert {

    /**
     * Convert user list to user map.
     * @param list - user list.
     * @return - user map.
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> users = new HashMap<>();
        for (User user : list) {
            users.put(user.getId(), user);
        }
        return users;
    }
}
