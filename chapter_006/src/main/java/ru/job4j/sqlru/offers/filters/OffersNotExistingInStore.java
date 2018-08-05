package ru.job4j.sqlru.offers.filters;

import ru.job4j.sqlru.store.DBException;
import ru.job4j.sqlru.offers.OfferBlank;
import ru.job4j.sqlru.store.OfferStore;

import java.util.Iterator;

/**
 * Вакансии не существующие в хранилище.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class OffersNotExistingInStore implements Iterable<OfferBlank> {
    private final Iterable<OfferBlank> offers;
    private final OfferStore offerStore;

    public OffersNotExistingInStore(
            final Iterable<OfferBlank> offers, final OfferStore offerStore) {
        this.offers = offers;
        this.offerStore = offerStore;
    }

    @Override
    public Iterator<OfferBlank> iterator() {
        return new OffersFilteringIterator(this.offers.iterator(), offerBlank -> {
            try {
                return offerBlank.notInStore(offerStore);
            } catch (DBException e) {
                throw new IllegalStateException(e);
            }
        });
    }
}
