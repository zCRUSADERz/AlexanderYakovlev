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
    public void whenOneUserOlderThanAnother() {
        User user1 = new User("One", 75);
        User user2 = new User("Second", 25);
        int result = user1.compareTo(user2);
        int expected = 1;
        assertThat(result, is(expected));
    }

    @Test
    public void whenOneUserYoungerThanAnother() {
        User user1 = new User("One", 15);
        User user2 = new User("Second", 25);
        int result = user1.compareTo(user2);
        int expected = -1;
        assertThat(result, is(expected));
    }

    @Test
    public void whenOneUserSameAgeThanAnother() {
        User user1 = new User("One", 25);
        User user2 = new User("Second", 25);
        int result = user1.compareTo(user2);
        int expected = 0;
        assertThat(result, is(expected));
    }
}