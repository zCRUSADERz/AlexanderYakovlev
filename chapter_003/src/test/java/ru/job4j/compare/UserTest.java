package ru.job4j.compare;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * User test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.01.2017
 * @version 1.0
 */
public class UserTest {

    @Test
    public void whenOneUserOLderThanAnother() {
        User user1 = new User("One", 75);
        User user2 = new User("Second", 25);
        int result = user1.compareTo(user2);
        int expected = 1;
        assertThat(result, is(expected));
    }
}