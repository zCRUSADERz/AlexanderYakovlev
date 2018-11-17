package ru.job4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Map<Integer, User> process(List<User> list) {
        return list.stream().collect(Collectors.toMap(User::getId, user -> user));
    }
}
