package ru.job4j.sqlru.offers.filters;

import ru.job4j.sqlru.offers.Offer;
import ru.job4j.sqlru.store.DBException;
import ru.job4j.sqlru.store.OfferStore;

import java.util.Iterator;

/**
 * Вакансии не существующие в хранилище.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class OffersNotExistingInStore implements Iterable<Offer> {
    private final Iterable<Offer> offers;
    private final OfferStore offerStore;

    public OffersNotExistingInStore(
            final Iterable<Offer> offers, final OfferStore offerStore) {
        this.offers = offers;
        this.offerStore = offerStore;
    }

    @Override
    public Iterator<Offer> iterator() {
        return new FilteringIterator<>(this.offers.iterator(), offerBlank -> {
            try {
                return offerBlank.notInStore(offerStore);
            } catch (DBException e) {
                throw new IllegalStateException(e);
            }
        });
    }
}
