package ru.job4j.broker;

import ru.job4j.broker.orderstore.ExchangeCups;
import ru.job4j.broker.orderstore.OrderStore;
import ru.job4j.broker.order.*;

/**
 * Trade system.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
public class TradeSystem {
    private ExchangeCups cups;

    public TradeSystem() {
        cups = new ExchangeCups();
    }

    /**
     * Processes the addition of a new order.
     * @param id - order id.
     * @param book - order book.
     * @param typeAction - order action: add or delete.
     * @param typeOrder - order type: ask or bid.
     * @param price - price.
     * @param volume - volume.
     */
    public void newOrder(int id, String book, TypeAction typeAction,
                         TypeOrder typeOrder, int price, int volume) {
        validateOrder(id, price, volume);
        validateBook(book);
        OrderStore orderStore = cups.orderStore(book, price);
        if (TypeAction.ADD == typeAction) {
            OrderAdd order = new OrderAdd(
                    new SimpleOrder(id, typeOrder, price, volume)
            );
            order.register(orderStore);
        } else if (TypeAction.DELETE == typeAction) {
            OrderDelete order = new OrderDelete(
                    new SimpleOrder(id, typeOrder, price, volume)
            );
            order.delete(orderStore);
        }
    }

    public String toString() {
        return cups.toString();
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
}
