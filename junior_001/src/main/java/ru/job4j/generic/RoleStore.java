package ru.job4j.generic;

import ru.job4j.generic.models.Role;

/**
 * Role store.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.01.2017
 * @version 1.0
 */
class RoleStore extends AbstractStore<Role> {

    RoleStore(int capacity) {
        super(new SimpleArray<Role>(capacity));
    }
}
