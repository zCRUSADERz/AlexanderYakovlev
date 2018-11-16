package ru.job4j.sqlru.offers.filters;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Фильтрующий итератор.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class FilteringIterator<E> implements Iterator<E> {
    private final Iterator<E> offerIterator;
    private final Predicate<E> filter;
    private E nextOffer;
    /**
     * true, если получена следующая вакансия
     * удовлетворяющая требованиям фильтра.
     */
    private boolean receivedNext = false;

    public FilteringIterator(
            final Iterator<E> offerIterator,
            final Predicate<E> filter) {
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
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iteration has no more elements");
        }
        this.receivedNext = false;
        return this.nextOffer;
    }

    private void receiveNextOffer() {
        while (!this.receivedNext && this.offerIterator.hasNext()) {
            E offer = this.offerIterator.next();
            if (filter.test(offer)) {
                this.nextOffer = offer;
                this.receivedNext = true;
            }
        }
    }
}
