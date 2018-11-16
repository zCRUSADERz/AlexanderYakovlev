package ru.job4j.broker.orders.type;

import ru.job4j.broker.orders.Order;

import java.util.Objects;

/**
 * Bid ордер. (типизированный ордер TypeOrders)
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 11.03.2018
 */
public class AskOrder extends AbstractTypeOrder implements AskOrders {
    private final Order simpleOrder;

    public AskOrder(Order simpleOrder) {
        super(simpleOrder);
        this.simpleOrder = simpleOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AskOrder askOrder = (AskOrder) o;
        return Objects.equals(simpleOrder, askOrder.simpleOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simpleOrder);
    }
}
