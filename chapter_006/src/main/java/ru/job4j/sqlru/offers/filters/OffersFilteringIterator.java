package ru.job4j.sqlru.offers.filters;

import ru.job4j.sqlru.offers.OfferBlank;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Фильтрующий итератор.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class OffersFilteringIterator implements Iterator<OfferBlank> {
    private final Iterator<OfferBlank> offerIterator;
    private final Predicate<OfferBlank> filter;
    private OfferBlank nextOffer;
    /**
     * true, если получена следующая вакансия
     * удовлетворяющая требованиям фильтра.
     */
    private boolean receivedNext = false;

    public OffersFilteringIterator(
            final Iterator<OfferBlank> offerIterator,
            final Predicate<OfferBlank> filter) {
        this.offerIterator = offerIterator;
        this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        if (!this.receivedNext) {
            receiveNextOffer();
        }
        return this.receivedNext;
    }

    @Override
    public OfferBlank next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iteration has no more elements");
        }
        this.receivedNext = false;
        return this.nextOffer;
    }

    private void receiveNextOffer() {
        while (!this.receivedNext && this.offerIterator.hasNext()) {
            OfferBlank offer = this.offerIterator.next();
            if (filter.test(offer)) {
                this.nextOffer = offer;
                this.receivedNext = true;
            }
        }
    }
}
