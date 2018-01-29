package ru.job4j.generic;

import ru.job4j.generic.models.User;

/**
 * User store.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.01.2017
 * @version 1.0
 */
class UserStore extends AbstractStore<User> {

    UserStore(int capacity) {
        super(new SimpleArray<User>(capacity));
    }
}
