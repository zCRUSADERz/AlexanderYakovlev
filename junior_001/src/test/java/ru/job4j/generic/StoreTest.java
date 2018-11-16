package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.generic.models.Role;
import ru.job4j.generic.models.User;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * Store test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.01.2017
 * @version 1.0
 */
public class StoreTest {
    private UserStore store;
    private User user;
    private User user2;

    @Before
    public void setStore() {
        store = new UserStore(10);
        user = new User();
        store.add(user);
        user2 = new User();
        store.add(user2);
    }

    @Test
    public void whenAddThenFindByIdReturnNotNull() {
        RoleStore store = new RoleStore(10);
        Role role = new Role();
        store.add(role);
        Role result = store.findById(role.getId());
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void whenFindByIdNotExistElementThenIdReturnNull() {
        User result = store.findById("");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenDeleteExistingElementThenReturnTrue() {
        boolean result = store.delete(user.getId());
        assertThat(result, is(true));
    }

    @Test
    public void whenDeleteNonExistentElementThenReturnFalse() {
        boolean result = store.delete("");
        assertThat(result, is(false));
    }

    @Test
    public void whenDeleteElementThenFindByIdRetunNull() {
        store.delete(user.getId());
        User result = store.findById(user.getId());
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenReplaceExistingElementThenReturnTrue() {
        boolean result = store.replace(user.getId(), user2);
        assertThat(result, is(true));
    }

    @Test
    public void whenReplaceNonExistentElementThenReturnFalse() {
        boolean result = store.replace("", user);
        assertThat(result, is(false));
    }

    @Test
    public void whenReplaceElementThenFindByIdRetunNull() {
        store.replace(user.getId(), new User());
        User result = store.findById(user.getId());
        assertThat(result, is(nullValue()));
    }
}