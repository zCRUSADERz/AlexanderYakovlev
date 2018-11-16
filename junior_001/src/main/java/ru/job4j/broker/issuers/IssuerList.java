package ru.job4j.broker.issuers;

import ru.job4j.broker.orders.type.AskOrders;
import ru.job4j.broker.orders.type.BidOrders;
import ru.job4j.broker.orders.type.wrappers.AskOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.BidOrderWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Список все эмитентов.
 * Содержит список эмитентов, и их биржевые стаканы.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 09.03.2018
 */
public class IssuerList {
    private final Map<String, Map<Integer, GlassPriceLevel<AskOrders, BidOrders>>> issuerList;

    public IssuerList() {
        this.issuerList = new HashMap<>();
    }

    /**
     * Возвращает регистратора ордеров. Если не существует эмитента, либо его
     * биржевого стакана, то будут созданы их представители.
     * @param book - эмитент.
     * @param price - цена ордера.
     * @return - регистратор ордеров.
     */
    public GlassPriceLevel<AskOrders, BidOrders> registrar(String book, int price) {
        GlassPriceLevel<AskOrders, BidOrders> registrar;
        Map<Integer, GlassPriceLevel<AskOrders, BidOrders>> issuer;
        if (issuerList.containsKey(book)) {
            issuer = issuerList.get(book);
            if (issuer.containsKey(price)) {
                registrar = issuer.get(price);
            } else {
                registrar = newRegistrar(price, issuer);
            }
        } else {
            registrar = newRegistrar(price, newIssuer(book));
        }
        return registrar;
    }

    /**
     * Возвращает регистратора Ask ордеров. Если не существует эмитента, либо его
     * биржевого стакана, то будут созданы их представители.
     * @param book - эмитент.
     * @param price - цена ордера.
     * @return - регистратор ордеров.
     */
    public AskRegistrar<AskOrders> askRegistrar(String book, int price) {
        return registrar(book, price);
    }

    /**
     * Возвращает регистратора Bid ордеров. Если не существует эмитента, либо его
     * биржевого стакана, то будут созданы их представители.
     * @param book - эмитент.
     * @param price - цена ордера.
     * @return - регистратор ордеров.
     */
    public BidRegistrar<BidOrders> bidRegistrar(String book, int price) {
        return registrar(book, price);
    }

    /**
     * Биржевые стаканы всех зарегистрированных в системе эмитентов.
     * @return - все биржевые стаканы.
     */
    public String toString() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        result.add("Эмитенты и их биржевые стаканы зарегистрированные в системе:");
        for (Map.Entry<String, Map<Integer, GlassPriceLevel<AskOrders, BidOrders>>> pair : issuerList.entrySet()) {
            result.add(pair.getKey());
            result.add("Продажа Цена Покупка");
            for (Map.Entry<Integer, GlassPriceLevel<AskOrders, BidOrders>> level : pair.getValue().entrySet()) {
                result.add(level.getValue().toString());
            }
        }
        return result.toString();
    }

    private synchronized Map<Integer, GlassPriceLevel<AskOrders, BidOrders>> newIssuer(
            String book) {
        Map<Integer, GlassPriceLevel<AskOrders, BidOrders>> result;
        if (issuerList.containsKey(book)) {
            result = issuerList.get(book);
        } else {
            result = new HashMap<>();
            issuerList.put(book, result);
        }
        return result;
    }

    private synchronized GlassPriceLevel<AskOrders, BidOrders> newRegistrar(
            int price, Map<Integer, GlassPriceLevel<AskOrders, BidOrders>> issuer) {
        GlassPriceLevel<AskOrders, BidOrders> result;
        if (issuer.containsKey(price)) {
            result = issuer.get(price);
        } else {
            result = new GlassPriceLevel<>(
                    new AskOrderWrapper(), new BidOrderWrapper(), price
            );
            issuer.put(price, result);
        }
        return result;
    }
}
