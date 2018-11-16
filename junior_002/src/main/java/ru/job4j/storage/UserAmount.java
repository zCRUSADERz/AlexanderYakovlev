package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Количество "чего-то" у пользователя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.04.2018
 */
@ThreadSafe
public class UserAmount {
    @GuardedBy("this")
    private volatile int amount;

    /**
     * @param amount - количество "чего-то".
     */
    public UserAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Возвращает "amount".
     * @return - amount.
     */
    public int get() {
        return amount;
    }

    /**
     * Транзакция перевода некоторого количества другому пользователю.
     * @param toUser - пользователь, которому нужно перевести.
     * @param transferAmount - количество для перевода.
     * @return - true, если транзакция выполнена.
     */
    public boolean transferTo(UserAmount toUser, int transferAmount) {
        boolean result = false;
        if (withdrawAmount(transferAmount)) {
            toUser.takeAmount(transferAmount);
            result = true;
        }
        return result;
    }

    /**
     * Принять некоторое количество.
     * @param amount - количество.
     */
    private synchronized void takeAmount(int amount) {
        this.amount += amount;
    }

    /**
     * Изъять некоторое количество, если доступно.
     * @param withdrawAmount - изымаемое количество.
     * @return - true, если изъято.
     */
    private synchronized boolean withdrawAmount(int withdrawAmount) {
        boolean result = false;
        if (withdrawAmount <= this.amount) {
            this.amount -= withdrawAmount;
            result = true;
        }
        return result;
    }
}
