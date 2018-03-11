package ru.job4j.broker.issuers;

import ru.job4j.broker.orders.type.AskOrders;
import ru.job4j.broker.orders.type.BidOrders;
import ru.job4j.broker.orders.type.wrappers.AskOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.BidOrderWrapper;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;

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
        TypeOrdersWrapper<AskOrders> askWrapper;
        TypeOrdersWrapper<BidOrders> bidWrapper;
        if (issuerList.containsKey(book)) {
            issuer = issuerList.get(book);
            if (issuer.containsKey(price)) {
                registrar = issuer.get(price);
            } else {
                askWrapper = new AskOrderWrapper();
                bidWrapper = new BidOrderWrapper();
                registrar = new GlassPriceLevel<>(askWrapper, bidWrapper, price);
                issuer.put(price, registrar);
            }
        } else {
            issuer = new HashMap<>();
            askWrapper = new AskOrderWrapper();
            bidWrapper = new BidOrderWrapper();
            registrar = new GlassPriceLevel<>(askWrapper, bidWrapper, price);
            issuer.put(price, registrar);
            issuerList.put(
                    book, issuer
            );
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
}
