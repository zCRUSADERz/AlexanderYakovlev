package ru.job4j.broker.orderstore;

import ru.job4j.broker.order.SimpleOrder;

/**
 * Interface for registering new order.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
public interface OrderRegistrar {
    void addAskOrder(SimpleOrder order);
    void addBidOrder(SimpleOrder order);
}
