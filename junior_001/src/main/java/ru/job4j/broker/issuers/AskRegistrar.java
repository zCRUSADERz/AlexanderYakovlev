package ru.job4j.broker.issuers;

import ru.job4j.broker.orders.type.AskOrders;

/**
 * Интерфейс регистратора заявок Ask.
 * Принимает заявки на регистрацию и удаление заявок.
 * Используется ордерами Add и Delete.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.03.2018
 */
public interface AskRegistrar<A extends AskOrders> {
    void addOrder(A order);
    void deleteOrder(A order);
}
