package ru.job4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * User convert to Map test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.01.2017
 * @version 1.0
 */
public class UserConvertTest {

    @Test
    public void whenUserListConvertToUserMap() {
        UserConvert convert = new UserConvert();
        User user1 = new User("1", "a");
        User user2 = new User("2", "a");
        List<User> list = new ArrayList<>(Arrays.asList(user1, user2));
        HashMap<Integer, User> result = convert.process(list);
        HashMap<Integer, User> expected = new HashMap<>();
        expected.put(user1.getId(), user1);
        expected.put(user2.getId(), user2);
        assertThat(result, is(expected));
    }
}