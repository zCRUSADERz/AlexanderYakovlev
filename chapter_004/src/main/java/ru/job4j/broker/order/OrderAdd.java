package ru.job4j.broker.order;

import ru.job4j.broker.TypeOrder;
import ru.job4j.broker.orderstore.OrderRegistrar;

public class OrderAdd {
    private final SimpleOrder order;

    public OrderAdd(SimpleOrder order) {
        this.order = order;
    }

    public void register(OrderRegistrar registrar) {
        if (TypeOrder.ASK == order.type()) {
            registrar.addAskOrder(order);
        } else if (TypeOrder.BID == order.type()) {
            registrar.addBidOrder(order);
        }
    }
}
