package ru.job4j.broker.orders.action;

import ru.job4j.broker.issuers.BidRegistrar;
import ru.job4j.broker.issuers.IssuerList;
import ru.job4j.broker.orders.type.BidOrders;

/**
 * Зарегистрировать ордер Bid.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.03.2018
 */
public class OrderBidAdd extends AbstractOrderAction<BidOrders> implements ActionOrders {
    private final String book;
    private final int price;
    private final IssuerList issuers;

    public OrderBidAdd(String book, int price, BidOrders order, IssuerList issuers) {
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
        BidRegistrar<BidOrders> registrar = issuers.bidRegistrar(book, price);
        registrar.addOrder(super.order());
    }
}
