package ru.job4j.broker.issuers;

import ru.job4j.broker.store.utils.BufferedIterator;
import ru.job4j.broker.orders.type.AskOrders;
import ru.job4j.broker.orders.type.BidOrders;
import ru.job4j.broker.store.TypeOrderRepositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Сделка.
 * Набирает доступные противоположные(ASK <-> BID) ордера из хранилищ,
 * с условием равного объема противоположных ордеров.
 *
 * В будущем нужно реализовать журнал исполненных ордеров. Перемещение средств
 * за исполненные ордера от покупателя к продавцу. И т.д. и т.п.
 * А так же реализовать это все в многопоточном режиме.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 09.03.2018
 */
public class Deal<A extends AskOrders, B extends BidOrders> {
    private final TypeOrderRepositories<A> askStore;
    private final TypeOrderRepositories<B> bidStore;
    // TODO Реализовать журнал сделок в котором и будем использовать эти ордера.
    private final List<A> askOrders;
    // TODO Тоже что и выше.
    private final List<B> bidOrders;

    public Deal(TypeOrderRepositories<A> askStore, TypeOrderRepositories<B> bidStore) {
        this.askStore = askStore;
        this.bidStore = bidStore;
        this.askOrders = new ArrayList<>();
        this.bidOrders = new ArrayList<>();
    }

    /**
     * Процесс набора доступных ордеров и их исполнение.
     */
    public void trade() {
        BufferedIterator<A> askIterator = askStore.iterator();
        BufferedIterator<B> bidIterator = bidStore.iterator();
        while (!askIterator.storeIsEmpty() && !bidIterator.storeIsEmpty()) {
            bidIterator.takeNext();
            askIterator.takeNext();
        }
        int askVolume = askIterator.volume();
        int bidVolume = bidIterator.volume();
        while (askVolume > bidVolume && !bidIterator.storeIsEmpty()) {
            bidIterator.takeNext();
            bidVolume = bidIterator.volume();
        }
        while (askVolume < bidVolume && !askIterator.storeIsEmpty()) {
            askIterator.takeNext();
            askVolume = askIterator.volume();
        }
        int minVolume = Integer.min(askIterator.volume(), bidIterator.volume());
        askOrders.addAll(askIterator.receive(minVolume));
        bidOrders.addAll(bidIterator.receive(minVolume));
    }
}
