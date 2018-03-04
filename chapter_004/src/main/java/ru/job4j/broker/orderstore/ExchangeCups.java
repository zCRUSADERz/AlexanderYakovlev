package ru.job4j.broker.orderstore;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Storage of all stock orderstore.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
public class ExchangeCups {
    private final Map<String, ExchangeCup> cups;

    public ExchangeCups() {
        cups = new HashMap<>();
    }

    /**
     * Return order store. If the glass is not found,
     * it will be automatically created.
     * @param book - book.
     * @param price - price.
     * @return - order store.
     */
    public OrderStore orderStore(String book, int price) {
        ExchangeCup result;
        if (cups.containsKey(book)) {
            result = cups.get(book);
        } else {
            result = new ExchangeCup(book);
            cups.put(book, result);
        }
        return result.orderStore(price);
    }

    public String toString() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        result.add("Эмитенты и их биржевые стаканы зарегистрированные в системе:");
        for (ExchangeCup exchangeCup : cups.values()) {
            result.add(exchangeCup.toString());
        }
        return result.toString();
    }
}
