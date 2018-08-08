package ru.job4j.sqlru.offers.filters;

import ru.job4j.sqlru.offers.Offer;

import java.util.Date;
import java.util.Iterator;

/**
 * Вакансии созданные не раньше определенной даты.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class OffersCreatedNotEarlierThan implements Iterable<Offer> {
    private final Iterable<Offer> offers;
    private final Date notEarlierThan;

    public OffersCreatedNotEarlierThan(
            final Iterable<Offer> offers,
            final Date notEarlierThan) {
        this.offers = offers;
        this.notEarlierThan = notEarlierThan;
    }

    @Override
    public Iterator<Offer> iterator() {
        return new FilteringIterator<>(
                this.offers.iterator(),
                offerBlank -> offerBlank.isCreatedLaterThan(this.notEarlierThan)
        );
    }
}
