package ru.job4j.storage;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тест хранилища пользователей.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.04.2018
 */
public class UserStorageTest {
    private ConcurrentMap<Integer, User> users;
    private UserStorage storage;

    @Before
    public void setStorage() {
        this.users = new ConcurrentHashMap<>();
        this.storage = new UserStorage(users);
    }

    @Test
    public void whenAdd() {
        User user = new User(1, 10);
        boolean result = storage.add(user);
        assertThat(result, is(true));
        assertThat(users.get(user.id()), is(user));
    }

    @Test
    public void whenAddExistUserThenUsersHaveOnlyOneUser() {
        User user = new User(2, 15);
        storage.add(user);
        boolean result = storage.add(new User(2, 100));
        assertThat(result, is(false));
        assertThat(users.keySet().size(), is(1));
        assertThat(users.get(user.id()).amount(), is(15));
    }

    @Test
    public void whenUpdate() {
        storage.add(new User(3, 20));
        User user = new User(3, 30);
        storage.update(user);
        assertThat(users.get(user.id()).amount(), is(30));
    }

    @Test
    public void whenUpdateNonExistUser() {
        boolean result = storage.update(new User(4, 35));
        assertThat(result, is(false));
    }

    @Test
    public void whenDeleteExistUser() {
        User user = new User(5, 1);
        storage.add(user);
        boolean result = storage.delete(user);
        assertThat(result, is(true));
        assertThat(users.isEmpty(), is(true));
    }

    @Test
    public void whenFromUserNotExist() {
        boolean result = storage.transfer(1, 1, 1);
        assertThat(result, is(false));
    }

    @Test
    public void whenToUserNotExist() {
        storage.add(new User(6, 23));
        boolean result = storage.transfer(6, 1, 1);
        assertThat(result, is(false));
    }

    @Test
    public void whenTransfer() {
        User from = new User(7, 7);
        User to = new User(8, 1);
        storage.add(from);
        storage.add(to);
        boolean result = storage.transfer(from.id(), to.id(), 7);
        assertThat(result, is(true));
    }
}