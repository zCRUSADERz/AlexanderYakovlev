package ru.job4j.sqlru.offers.filters;

import ru.job4j.sqlru.offers.Offer;
import ru.job4j.sqlru.offers.Offers;
import ru.job4j.sqlru.offers.SpecificOffer;

import java.util.Iterator;

/**
 * Преобразует образцы вакансий в вакансии с определенной специальностью.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SpecificOffers<E extends SpecificOffer> implements Iterable<E> {
    private final Iterable<Offer> offers;
    private final Offers<E> specificOffers;

    public SpecificOffers(
            final Iterable<Offer> offers,
            final Offers<E> specificOffers) {
        this.offers = offers;
        this.specificOffers = specificOffers;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<Offer> offerIterator
                    = new FilteringIterator<>(
                            offers.iterator(), specificOffers::isOffer
            );

            @Override
            public boolean hasNext() {
                return this.offerIterator.hasNext();
            }

            @Override
            public E next() {
                return this.offerIterator.next()
                        .processToOffer(specificOffers);
            }
        };
    }
}
