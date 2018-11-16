package ru.job4j.sqlru.parsers;

import ru.job4j.sqlru.offers.Offer;

import java.util.*;

/**
 * Парсер вакансий с форума sql.ru.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuOffersParser implements Iterable<Offer> {
    private final Iterable<OfferTopic> offerTopics;

    public SqlRuOffersParser(final Iterable<OfferTopic> offerTopics) {
        this.offerTopics = offerTopics;
    }

    @Override
    public Iterator<Offer> iterator() {
        return new OffersIterator(this.offerTopics.iterator());
    }

    private class OffersIterator implements Iterator<Offer> {
        private final Iterator<OfferTopic> offerTopicsIterator;

        public OffersIterator(final Iterator<OfferTopic> offerTopicsIterator) {
            this.offerTopicsIterator = offerTopicsIterator;
        }

        @Override
        public boolean hasNext() {
            return this.offerTopicsIterator.hasNext();
        }

        @Override
        public Offer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration has no more elements");
            }
            try {
                return this.offerTopicsIterator.next().parse();
            } catch (ParseException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
