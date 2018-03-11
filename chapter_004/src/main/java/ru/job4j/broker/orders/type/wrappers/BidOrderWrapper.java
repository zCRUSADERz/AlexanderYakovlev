package ru.job4j.broker.orders.type.wrappers;

import ru.job4j.broker.orders.Order;
import ru.job4j.broker.orders.type.BidOrder;
import ru.job4j.broker.orders.type.BidOrders;

/**
 * Реализация оборачивания простых ордеров в типизированный BidOrders.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 09.03.2018
 */
public class BidOrderWrapper implements TypeOrdersWrapper<BidOrders> {

    /**
     * Оборачивает простой ордер в типизированный BidOrders.
     * @param order - простой ордер.
     * @return - Bid order.
     */
    @Override
    public BidOrders wrap(Order order) {
        return new BidOrder(order);
    }
}
