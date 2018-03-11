package ru.job4j.broker.orders.action;

import ru.job4j.broker.issuers.AskRegistrar;
import ru.job4j.broker.issuers.IssuerList;
import ru.job4j.broker.orders.type.AskOrders;

/**
 * Зарегистрировать ордер Ask.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.03.2018
 */
public class OrderAskAdd extends AbstractOrderAction<AskOrders> implements ActionOrders {
    private final String book;
    private final int price;
    private final IssuerList issuers;

    public OrderAskAdd(String book, int price, AskOrders order, IssuerList issuers) {
        super(order);
        this.book = book;
        this.price = price;
        this.issuers = issuers;
    }

    /**
     * Зарегистрировать ордер.
     */
    @Override
    public void doAction() {
        AskRegistrar<AskOrders> registrar = issuers.askRegistrar(book, price);
        registrar.addOrder(super.order());
    }
}
