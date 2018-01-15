package ru.job4j.compare;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Sort user test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.01.2017
 * @version 1.0
 */
public class SortUserTest {

    @Test
    public void whenUnSortedListSortThenSortedSet() {
        SortUser sortUser = new SortUser();
        User user1 = new User("1", 50);
        User user2 = new User("2", 30);
        User user3 = new User("3", 40);
        List<User> list = new ArrayList<>(Arrays.asList(user1, user2, user3));
        Set<User> result = sortUser.sort(list);
        Set<User> expected = new TreeSet<>(Arrays.asList(user2, user3, user1));
        assertThat(result, is(expected));
    }

    @Test
    public void whenUnSortedListSortByNameLengthThenSortedList() {
        SortUser sortUser = new SortUser();
        User user1 = new User("Владимир", 25);
        User user2 = new User("Иван", 30);
        User user3 = new User("Сергей", 50);
        List<User> list = Arrays.asList(user1, user2, user3);
        List<User> result = sortUser.sortNameLength(list);
        List<User> expected = Arrays.asList(user2, user3, user1);
        assertThat(result, is(expected));
    }

    @Test
    public void whenUnSortedListSortByAllFieldsThenSortedList() {
        SortUser sortUser = new SortUser();
        User user1 = new User("Владимир", 45);
        User user2 = new User("Иван", 30);
        User user3 = new User("Сергей", 50);
        User user4 = new User("Владимир", 25);
        List<User> list = Arrays.asList(user1, user2, user3, user4);
        List<User> result = sortUser.sortByAllFields(list);
        List<User> expected = Arrays.asList(user4, user1, user2, user3);
        assertThat(result, is(expected));
    }
}