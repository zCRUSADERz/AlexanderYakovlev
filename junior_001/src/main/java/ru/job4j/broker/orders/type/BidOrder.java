package ru.job4j.broker.orders.type;

import ru.job4j.broker.orders.Order;

import java.util.Objects;

/**
 * Ask ордер. (типизированный ордер TypeOrders)
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 11.03.2018
 */
public class BidOrder extends AbstractTypeOrder implements BidOrders {
    private final Order simpleOrder;

    public BidOrder(Order simpleOrder) {
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
        BidOrder bidOrder = (BidOrder) o;
        return Objects.equals(simpleOrder, bidOrder.simpleOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simpleOrder);
    }
}
