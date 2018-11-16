package ru.job4j.broker.orders.action;

import ru.job4j.broker.orders.type.TypeOrders;

/**
 * Абстракция ордера с действием.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.03.2018
 */
public interface ActionOrders extends TypeOrders {

    /**
     * Совершить действие.
     */
    void doAction();
}
