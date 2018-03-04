package ru.job4j.broker.orderstore;

import ru.job4j.broker.order.*;

import java.util.*;

/**
 * Price level.
 * It is the level of the price in a stock cup.
 * Contains the orders related to this level of prices.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
class PriceLevel implements OrderStore {
    private final int price;
    private final LinkedList<SimpleOrder> askOrders;
    private final LinkedList<SimpleOrder> bidOrders;

    PriceLevel(int price) {
        this.price = price;
        askOrders = new LinkedList<>();
        bidOrders = new LinkedList<>();
    }

    /**
     * Add ask order at the end of ask orders queue.
     * @param order - added order.
     */
    @Override
    public void addAskOrder(SimpleOrder order) {
        askOrders.add(order);
        trade();
    }

    /**
     * Add ask order at the end of bid orders queue.
     * @param order - added order.
     */
    @Override
    public void addBidOrder(SimpleOrder order) {
        bidOrders.add(order);
        trade();
    }

    /**
     * Delete order. If the order is not removed completely,
     * the new order will be placed in the same position as the deleted one
     * @param order - deleted order
     */
    @Override
    public void deleteAskOrder(SimpleOrder order) {
        deleteOrder(askOrders, order);
    }

    /**
     * Delete order. If the order is not removed completely,
     * the new order will be placed in the same position as the deleted one
     * @param order - deleted order
     */
    @Override
    public void deleteBidOrder(SimpleOrder order) {
        deleteOrder(bidOrders, order);
    }

    @Override
    public String toString() {
        String result;
        int askVolume = calcVolume(askOrders);
        int bidVolume = calcVolume(bidOrders);
        if (askVolume == 0 && bidVolume != 0) {
            result = String.format("%7d %-4d", bidVolume, price);
        } else if (askVolume != 0 && bidVolume == 0) {
            result = String.format("        %-4d %-7d", price, askVolume);
        } else {
            result = "";
        }
        return result;
    }

    public String askOrdersToString() {
        return ordersToString(askOrders);
    }

    public String bidOrdersToString() {
        return ordersToString(bidOrders);
    }

    private String ordersToString(Collection<SimpleOrder> list) {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        for (SimpleOrder order : list) {
            result.add(order.toString());
        }
        return result.toString();
    }

    private void deleteOrder(List<SimpleOrder> list, SimpleOrder order) {
        ListIterator<SimpleOrder> iterator = list.listIterator();
        while (iterator.hasNext()) {
            SimpleOrder askOrder = iterator.next();
            if (askOrder.equals(order)) {
                SimpleOrder result = askOrder.delete(order);
                if (result.volume() != 0) {
                    iterator.set(result);
                } else {
                    iterator.remove();
                }
                break;
            }
        }
    }

    /**
     * Executes ask with bid orders.
     */
    private void trade() {
        if (askOrders.size() > 0 && bidOrders.size() > 0) {
            SimpleOrder ask = askOrders.poll();
            SimpleOrder bid = bidOrders.poll();
            if (ask.volume() > bid.volume()) {
                askOrders.addFirst(ask.execute(bid));
                trade();
            } else if (ask.volume() < bid.volume()) {
                bidOrders.addFirst(ask.execute(bid));
                trade();
            }
        }
    }

    /**
     * Calculate total volume of all ask or bid orders.
     */
    private int calcVolume(Collection<SimpleOrder> list) {
        int result = 0;
        for (SimpleOrder order : list) {
            result += order.volume();
        }
        return result;
    }
}
