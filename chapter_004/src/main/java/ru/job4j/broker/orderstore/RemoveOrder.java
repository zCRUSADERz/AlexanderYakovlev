package ru.job4j.broker.orderstore;

import ru.job4j.broker.order.SimpleOrder;

/**
 * Order removal interface.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
public interface RemoveOrder {
    void deleteAskOrder(SimpleOrder order);
    void deleteBidOrder(SimpleOrder order);
}
