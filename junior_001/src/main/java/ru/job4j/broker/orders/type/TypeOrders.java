package ru.job4j.broker.orders.type;

import ru.job4j.broker.orders.Order;

/**
 * Типизированный ордер(Ask or Bid). Интерфейс представляет общее поведение
 * группы типизированных ордеров.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 08.03.2018
 */
public interface TypeOrders extends Order {
    Order order();
}
