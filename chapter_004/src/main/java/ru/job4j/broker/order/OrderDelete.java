package ru.job4j.broker.order;

import ru.job4j.broker.TypeOrder;
import ru.job4j.broker.orderstore.RemoveOrder;

public class OrderDelete {
    private final SimpleOrder order;

    public OrderDelete(SimpleOrder order) {
        this.order = order;
    }

    public void delete(RemoveOrder removeOrder) {
        if (TypeOrder.ASK == order.type()) {
            removeOrder.deleteAskOrder(order);
        } else if (TypeOrder.BID == order.type()) {
            removeOrder.deleteBidOrder(order);
        }
    }
}
