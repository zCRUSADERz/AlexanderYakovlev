package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

/**
 * Пользователь.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.04.2018
 */
@ThreadSafe
public class User {
    private final int id;
    private final UserAmount amount;

    /**
     * @param id - уникальный идентификатор.
     * @param amount - количество "чего-то".
     */
    public User(int id, int amount) {
        this.id = id;
        this.amount = new UserAmount(amount);
    }

    public int id() {
        return this.id;
    }

    public int amount() {
        return this.amount.get();
    }

    /**
     * Перевод "чего-то" другому пользователю
     * @param toUser - кому перевести.
     * @param transferAmount - количество для перевода.
     * @return - true, если переведено.
     */
    public boolean transferTo(User toUser, int transferAmount) {
        return this.amount.transferTo(toUser.amount, transferAmount);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
