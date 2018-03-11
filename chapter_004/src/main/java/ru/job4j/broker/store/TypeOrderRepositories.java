package ru.job4j.broker.store;

import ru.job4j.broker.orders.type.TypeOrders;
import ru.job4j.broker.store.utils.BufferedIterator;

/**
 * Хранилище типизированных (Ask или Bid) ордеров.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 11.03.2018
 */
public interface TypeOrderRepositories<T extends TypeOrders> {

    /**
     * Добавляет ордер в хранилище.
     * @param order - добавляемый ордер
     */
    void addOrder(T order);

    /**
     * Удаляет из хранилища и возвращает вызывающему.
     * @return - наименьший ордер из хранилища.
     */
    T withdrawOrder(T order);

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

    /**
     * Возвращает буфферизированный итератор типизированных ордеров, работающий
     * с этим хранилищем.
     * @return - буфферизированный итератор.
     */
    BufferedIterator<T> iterator();
}
