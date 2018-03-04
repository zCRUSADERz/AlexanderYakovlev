package ru.job4j.broker.orderstore;

import java.util.*;

/**
 * Exchange cup.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
class ExchangeCup {
    private final String book;
    private final SortedMap<Integer, PriceLevel> cup;

    ExchangeCup(String book) {
        this.book = book;
        cup = new TreeMap<>(Comparator.reverseOrder());
    }

    /**
     * Return order store. If the PriceLevel(Order store) is not found,
     * it will be automatically created.
     * @param price - price.
     * @return - order store.
     */
    public OrderStore orderStore(int price) {
        OrderStore result;
        if (cup.containsKey(price)) {
            result = cup.get(price);
        } else {
            PriceLevel priceLevel = new PriceLevel(price);
            cup.put(price, priceLevel);
            result = priceLevel;
        }
        return result;
    }

    public String toString() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        result.add(book);
        if (cup.size() != 0) {
            result.add("Продажа Цена Покупка");
            for (PriceLevel level : cup.values()) {
                String levelString = level.toString();
                if (levelString.length() != 0) {
                    result.add(levelString);
                }
            }
        }
        return result.toString();
    }
}
