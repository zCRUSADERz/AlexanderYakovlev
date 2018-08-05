package ru.job4j.sqlru.offers.filters;

import ru.job4j.sqlru.offers.OfferBlank;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Вакансия созданные не раньше определенной даты.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class OffersCreatedNotEarlierThan implements Iterable<OfferBlank> {
    /**
     * Вакансии обязательно должны быть отсортированны
     * по дате создания в порядке убывания.
     */
    private final Iterable<OfferBlank> sortedByCreatedDateOffers;
    /**
     * Не раньше этой даты.
     */
    private final Date notEarlierThan;

    public OffersCreatedNotEarlierThan(
            final Iterable<OfferBlank> sortedByCreatedDateOffers,
            final Date notEarlierThan) {
        this.sortedByCreatedDateOffers = sortedByCreatedDateOffers;
        this.notEarlierThan = notEarlierThan;
    }

    @Override
    public Iterator<OfferBlank> iterator() {
        return new OffersIterator(
                this.sortedByCreatedDateOffers.iterator(),
                offerBlank -> offerBlank.isCreatedLaterThan(this.notEarlierThan)
        );
    }

    /**
     * Итератор, который при получении вакансии созданной
     * ранее определенной даты прекращает работу.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 04.08.2018
     */
    private class OffersIterator implements Iterator<OfferBlank> {
        private final Iterator<OfferBlank> offerIterator;
        private final Predicate<OfferBlank> filter;
        private OfferBlank nextOffer;
        private boolean receivedNext = false;
        private boolean nextOffersCreatedEarlier = false;

        public OffersIterator(
                final Iterator<OfferBlank> offerIterator,
                final Predicate<OfferBlank> filter) {
            this.offerIterator = offerIterator;
            this.filter = filter;
        }

        @Override
        public boolean hasNext() {
            if (!this.nextOffersCreatedEarlier && !this.receivedNext) {
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
            while (!this.receivedNext && !this.nextOffersCreatedEarlier
                    && this.offerIterator.hasNext()) {
                OfferBlank offer = this.offerIterator.next();
                if (filter.test(offer)) {
                    this.nextOffer = offer;
                    this.receivedNext = true;
                } else {
                    this.nextOffersCreatedEarlier = true;
                }
            }
        }
    }
}
