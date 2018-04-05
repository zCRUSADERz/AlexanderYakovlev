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
    /**
     * Id пользователя. Используется для решения проблемы DeadLock.
     * Сначала блокируется пользователь с наименьшим Id.
     */
    private final int userId;
    @GuardedBy("this")
    private volatile int amount;

    /**
     * @param userId - Id пользователя.
     * @param amount - количество "чего-то".
     */
    public UserAmount(int userId, int amount) {
        this.userId = userId;
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
     * Во избежание проблемы DeadLock, сначала блокируется пользователь с
     * наименьшим Id.
     * @param toUser - пользователь, которому нужно перевести.
     * @param transferAmount - количество для перевода.
     * @return - true, если транзакция выполнена.
     */
    public boolean transferTo(UserAmount toUser, int transferAmount) {
        boolean result = false;
        if (this.userId < toUser.userId) {
            if (withdrawAmount(transferAmount)) {
                toUser.takeAmount(transferAmount);
                result = true;
            }
        } else {
            if (toUser.takeAmountFrom(this, transferAmount)) {
                result = true;
            }
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
     * Изымает количество у другого пользователя и добавляет к себе.
     * Метод используется в случае если нужно заблокировать сначала пользователя,
     * который должен получить некоторое количество.
     * @param from - откуда изъять.
     * @param amount - количество.
     * @return - true, если транзакция выполнена.
     */
    private synchronized boolean takeAmountFrom(UserAmount from, int amount) {
        boolean result = false;
        if (from.withdrawAmount(amount)) {
            this.amount += amount;
            result = true;
        }
        return result;
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
