package ru.job4j.broker.orders.type;

import ru.job4j.broker.orders.Order;

/**
 * Абстрактная реализация типизированных ордеров.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 11.03.2018
 */
public abstract class AbstractTypeOrder implements TypeOrders {
    private final Order simpleOrder;

    public AbstractTypeOrder(Order simpleOrder) {
        this.simpleOrder = simpleOrder;
    }

    /**
     * Возвращает меньшую абстракцию ордера.
     * @return - простой ордер.
     */
    @Override
    public Order order() {
        return simpleOrder;
    }

    /**
     * Возвращает объем ордера.
     * @return - volume.
     */
    @Override
    public int volume() {
        return simpleOrder.volume();
    }

    /**
     * Проверяет пустой ли ордер.
     * @return - true, if empty(volume == 0).
     */
    @Override
    public boolean isEmpty() {
        return simpleOrder.isEmpty();
    }

    /**
     * Возвращает строковое представление ордера.
     * @return - описание ордера.
     */
    @Override
    public String toString() {
        return simpleOrder.toString();
    }
}
