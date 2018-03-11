package ru.job4j.broker.orders.action;

import ru.job4j.broker.issuers.AskRegistrar;
import ru.job4j.broker.issuers.IssuerList;
import ru.job4j.broker.orders.type.AskOrders;

/**
 * Удалить из системы ордер Ask.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.03.2018
 */
public class OrderAskDelete extends AbstractOrderAction<AskOrders> implements ActionOrders {
    private final String book;
    private final int price;
    private final IssuerList issuers;

    public OrderAskDelete(String book, int price, AskOrders order, IssuerList issuers) {
        super(order);
        this.book = book;
        this.price = price;
        this.issuers = issuers;
    }

    /**
     * Удалить ордер из системы.
     */
    @Override
    public void doAction() {
        AskRegistrar<AskOrders> registrar = issuers.askRegistrar(book, price);
        registrar.deleteOrder(super.order());
    }
}
