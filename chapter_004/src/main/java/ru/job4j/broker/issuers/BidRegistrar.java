package ru.job4j.broker.issuers;

import ru.job4j.broker.orders.type.BidOrders;

/**
 * Интерфейс регистратора заявок Bid.
 * Принимает заявки на регистрацию и удаление заявок.
 * Используется ордерами Add и Delete.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.03.2018
 */
public interface BidRegistrar<B extends BidOrders> {
    void addOrder(B order);
    void deleteOrder(B order);
}
