package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsNot.not;

/**
 * User test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.01.2017
 * @version 1.0
 */
public class UserTest {

    @Test
    public void whenNewUserThenNewUniqueId() {
        User user1 = new User("1", "a");
        User user2 = new User("2", "a");
        int result = user1.getId();
        int expected = user2.getId();
        assertThat(result, not(expected));
    }
}