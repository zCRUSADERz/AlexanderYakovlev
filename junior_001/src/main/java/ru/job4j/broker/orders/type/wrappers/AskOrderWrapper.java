package ru.job4j.broker.orders.type.wrappers;

import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.type.AskOrder;
import ru.job4j.broker.orders.type.AskOrders;

/**
 * Реализация оборачивания простых ордеров в типизированный AskOrders.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 09.03.2018
 */
public class AskOrderWrapper implements TypeOrdersWrapper<AskOrders> {

    /**
     * Оборачивает простой ордер в типизированный AskOrders.
     * @param order - простой ордер.
     * @return - Ask order.
     */
    @Override
    public AskOrders wrap(Order order) {
        return new AskOrder(order);
    }
}
