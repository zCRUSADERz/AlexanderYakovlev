package ru.job4j.sqlru.offers;

import ru.job4j.sqlru.store.DBException;
import ru.job4j.sqlru.store.OfferStore;

/**
 * Сохраняет все вакансии в хранилище.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class OffersSaveToStore<E extends SpecificOffer> {
    private final Iterable<E> offers;
    private final OfferStore store;

    public OffersSaveToStore(
            final Iterable<E> offers, final OfferStore store) {
        this.offers = offers;
        this.store = store;
    }

    /**
     * Сохранить все Java вакансии в хранилище.
     * @throws DBException if store throw DBException.
     */
    public void save() throws DBException {
        for (E offer : this.offers) {
            offer.saveToStore(this.store);
        }
    }
}
