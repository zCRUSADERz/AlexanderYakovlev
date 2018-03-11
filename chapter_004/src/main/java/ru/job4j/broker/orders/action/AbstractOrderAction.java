package ru.job4j.broker.orders.action;

import ru.job4j.broker.orders.type.TypeOrders;

/**
 * Абстракция ордера с действием.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.03.2018
 */
public abstract class AbstractOrderAction<T extends TypeOrders> implements ActionOrders {
    private final T order;

    public AbstractOrderAction(T order) {
        this.order = order;
    }

    /**
     * Возвращает вложенный ордер.
     * @return - вложенный ордер.
     */
    @Override
    public T order() {
        return order;
    }

    /**
     * Возвращает объем ордера.
     * @return - объем ордера
     */
    @Override
    public int volume() {
        return order.volume();
    }

    /**
     * Проверяет пустой ли ордер.
     * @return - true, если пуст(volume == 0).
     */
    @Override
    public boolean isEmpty() {
        return order.isEmpty();
    }
}
