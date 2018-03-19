package ru.job4j.broker.store;

import ru.job4j.broker.orders.Order;

/**
 * Хранилище простых ордеров.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 11.03.2018
 */
public interface OrderRepositories {

    /**
     * Добавляет простой ордер в хранилище.
     * @param order - добавляемый ордер
     */
    void addOrder(Order order);

    /**
     * Удаляет из хранилища и возвращает вызывающему.
     * @return - наименьший ордер из хранилища.
     */
    Order withdrawOrder();

    /**
     * Удаляет ордер эквивалентный переданному, и передает дальше.
     * @param order - ордер, на основе которого будет найден ордер в хранилище.
     * @return - ордер из хранилища, если был найден, или пустой ордер.
     */
    Order withdrawOrder(Order order);

    /**
     * Возвращает ордер в начало очереди.
     * @param order - возвращаемый ордер.
     */
    void returnOrder(Order order);

    /**
     * Проверяет пусто ли хранилище.
     * @return - true, если пусто.
     */
    boolean isEmpty();

    /**
     * Вовзвращает объем всех ордеров в хранилище.
     * @return - объем всех ордеров в хранилище.
     */
    int volume();
}
