package ru.job4j.broker.issuers;

import ru.job4j.broker.orders.type.AskOrders;
import ru.job4j.broker.orders.type.BidOrders;
import ru.job4j.broker.orders.type.wrappers.TypeOrdersWrapper;
import ru.job4j.broker.store.OrderRepository;
import ru.job4j.broker.store.TypeOrderRepositories;

import java.util.StringJoiner;

/**
 * Уровень цены в биржевом стакане.
 * Является регистратором ордеров Ask и Bid. При регистрации нового ордера
 * запускает новую сделку.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 06.03.2018
 */
public class GlassPriceLevel<A extends AskOrders, B extends BidOrders>
        implements AskRegistrar<A>, BidRegistrar<B> {
    private final int price;
    /**
     * Хранилище Ask ордеров.
     */
    private final TypeOrderRepositories<A> askRepository;
    /**
     * Хранилище Bid ордеров.
     */
    private final TypeOrderRepositories<B> bidRepository;

    public GlassPriceLevel(TypeOrdersWrapper<A> askWrapper,
                           TypeOrdersWrapper<B> bidWrapper, int price) {
        this.price = price;
        askRepository = new OrderRepository<A>(askWrapper) { };
        bidRepository = new OrderRepository<B>(bidWrapper) { };
    }

    /**
     * Добавляет Ask ордер в хранилище.
     * @param order - ордер, который будет помещен в хранилище.
     */
    public void addOrder(A order) {
        askRepository.addOrder(order);
        trade();
    }

    /**
     * Добавляет Bid ордер в хранилище.
     * @param order - ордер, который будет помещен в хранилище.
     */
    public void addOrder(B order) {
        bidRepository.addOrder(order);
        trade();
    }

    /**
     * Удаляет Ask ордер из хранилища.
     * @param order - ордер на основе которого будет найден ордер в хранилище.
     */
    public void deleteOrder(A order) {
        askRepository.withdrawOrder(order);
    }

    /**
     * Удаляет Bid ордер из хранилища.
     * @param order - ордер на основе которого будет найден ордер в хранилище.
     */
    public void deleteOrder(B order) {
        bidRepository.withdrawOrder(order);

    }

    /**
     * Возвращает строку с всеми зарегистрированными ордерами.
     * @return - все зарегистрированные ордера в системе.
     */
    public String ordersToString() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        String askOrders = askRepository.toString();
        if (askOrders.isEmpty()) {
            result.add("Ask orders: no one");
        } else {
            result.add("Ask orders:");
            result.add(askOrders);
        }
        String bidOrders = bidRepository.toString();
        if (bidOrders.isEmpty()) {
            result.add("Bid orders: no one");
        } else {
            result.add("Bid orders:");
            result.add(bidOrders);
        }
        return result.toString();
    }

    /**
     * Строит строку представляющую уровень в биржевом стакане.
     * @return - строка - уровень в стакане.
     */
    @Override
    public String toString() {
        String result;
        int askVolume = askRepository.volume();
        int bidVolume = bidRepository.volume();
        if (askVolume == 0 && bidVolume != 0) {
            result = String.format("%7d %-4d", bidVolume, price);
        } else if (askVolume != 0 && bidVolume == 0) {
            result = String.format("        %-4d %-7d", price, askVolume);
        } else {
            result = "";
        }
        return result;
    }

    /**
     * Запускает новую сделку, которая исполнит все противоположно-направленные
     * ордера.
     */
    private void trade() {
        if (!askRepository.isEmpty() && !bidRepository.isEmpty()) {
            new Deal<>(askRepository, bidRepository).trade();
        }
    }
}
