package ru.job4j.broker;

import ru.job4j.broker.issuers.IssuerList;
import ru.job4j.broker.orders.SimpleOrder;
import ru.job4j.broker.orders.action.*;
import ru.job4j.broker.orders.type.wrappers.AskOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.BidOrderWrapper;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TradeSystem {
    // TODO нужен интерфейс.
    private final IssuerList issuerList = new IssuerList();

    /**
     * Processes the addition of a new order.
     * @param id - order id.
     * @param book - order book.
     * @param typeAction - order action: add or delete.
     * @param typeOrder - order type: ask or bid.
     * @param price - price.
     * @param volume - volume.
     */
    public void newOrder(int id, String book, String typeAction,
                         String typeOrder, int price, int volume) {
        validateOrder(id, price, volume);
        validateBook(book);
        validateAction(typeAction);
        validateType(typeOrder);
        ActionOrders order;
        if ("ask".equals(typeOrder.toLowerCase())) {
            AskOrderWrapper wrapper = new AskOrderWrapper();
            if ("add".equals(typeAction.toLowerCase())) {
                order = new OrderAskAdd(
                        book, price,
                        wrapper.wrap(new SimpleOrder(id, volume)), issuerList
                );
                order.doAction();
            } else if ("delete".equals(typeAction.toLowerCase())) {
                order = new OrderAskDelete(
                        book, price,
                        wrapper.wrap(new SimpleOrder(id, volume)), issuerList
                );
                order.doAction();
            }
        }
        if ("bid".equals(typeOrder.toLowerCase())) {
            BidOrderWrapper wrapper = new BidOrderWrapper();
            if ("add".equals(typeAction.toLowerCase())) {
                order = new OrderBidAdd(
                        book, price,
                        wrapper.wrap(new SimpleOrder(id, volume)), issuerList
                );
                order.doAction();
            } else if ("delete".equals(typeAction.toLowerCase())) {
                order = new OrderBidDelete(
                        book, price,
                        wrapper.wrap(new SimpleOrder(id, volume)), issuerList
                );
                order.doAction();
            }
        }
    }

    public String toString() {
        return issuerList.toString();
    }

    private void validateOrder(int id, int price, int volume) {
        if (id < 0 || price < 0 || volume <= 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Id, price, volume should not be less than zero."
                                    + "Id: %d, Price: %d, volume: %d",
                            id, price, volume
                    )
            );
        }
    }

    private void validateBook(String book) {
        if (book == null) {
            throw new IllegalArgumentException("Book should not be null");
        }
    }

    private void validateAction(String typeAction) {
        if (typeAction == null) {
            throw new IllegalArgumentException("Type action should not be null");
        }
        if (!("add".equals(typeAction.toLowerCase())
                || "delete".equals(typeAction.toLowerCase()))) {
            throw new IllegalArgumentException("Type action should be: add or delete.");
        }
    }

    private void validateType(String typeOrder) {
        if (typeOrder == null) {
            throw new IllegalArgumentException("Type order should not be null");
        }
        if (!("ask".equals(typeOrder.toLowerCase())
                || "bid".equals(typeOrder.toLowerCase()))) {
            throw new IllegalArgumentException("Type order should be: ask or bid.");
        }
    }
}
