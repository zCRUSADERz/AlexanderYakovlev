package ru.job4j.broker.store.utils;

import ru.job4j.broker.orders.type.TypeOrders;

import java.util.Collection;

/**
 * Итератор ордеров с накоплением в буфер.
 * Накапливает простые ордера в буфер, и по команде receive() возвращает все
 * ордера. Если указан объем (receive(volume)), то из буфера будут выданы
 * ордера с общим объемом равным запрашиваемому. Лишние ордера в буфере будут
 * немедленно возвращены обратно в хранилище.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 09.03.2018
 */
public interface BufferedIterator<T extends TypeOrders> {

    /**
     * Проверяет пусто ли хранилище.
     * @return - true, если пусто.
     */
    boolean storeIsEmpty();

    /**
     * Берет следующий ордер из хранилища.
     */
    void takeNext();

    /**
     * Объем накопленных в буффере ордеров.
     * @return - бъем накопленных в буффере ордеров.
     */
    int volume();

    /**
     * Возвращает ордера с запрашиваемым объемом. Все лишние ордера в буффере
     * после вызова будут немедленно возвращены обратно в хранилище.
     * @param volume - запрашиваемый объем.
     * @return - коллекцию ордеров с запрашиваемым объемом.
     */
    Collection<T> receive(int volume);
}
