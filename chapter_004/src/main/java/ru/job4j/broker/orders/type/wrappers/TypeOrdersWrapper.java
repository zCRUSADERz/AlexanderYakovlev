package ru.job4j.broker.orders.type.wrappers;

import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.type.TypeOrders;

/**
 * Интерфейс обертки ордеров в типизированные.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 09.03.2018
 */
public interface TypeOrdersWrapper<T extends TypeOrders> {
    T wrap(Order order);
}
