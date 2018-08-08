package ru.job4j.sqlru.offers.filters;

import org.junit.Test;
import ru.job4j.sqlru.offers.Offer;
import ru.job4j.sqlru.utils.SimpleDate;
import ru.job4j.sqlru.store.OfferStore;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class OffersNotExistingInStoreTest {
    private final OfferStore store = new OfferStore() {
        @Override
        public boolean findById(int id)  {
            return id == 2 || id == 4;
        }

        @Override
        public void save(int id, String title, String text, SimpleDate created, String url) {
        }

        @Override
        public void close() {
        }
    };

    @Test
    public void whenSomeOffersExistInStoreThenIterableHasNotThisOffers() {
        Offer expectedFirst = new Offer(
                1, "Offer1", "JavaOffer",
                new SimpleDate(new Date()), "some site"
        );
        Offer expectedSecond = new Offer(
                3, "Offer3", "JavaOffer",
                new SimpleDate(new Date()), "some site"
        );
        Offer expectedThird = new Offer(
                5, "Offer5", "JavaOffer",
                new SimpleDate(new Date()), "some site"
        );
        Iterator<Offer> iterator = new OffersNotExistingInStore(
                Arrays.asList(
                        expectedFirst,
                        new Offer(
                                2, "Offer2", "JavaOffer",
                                new SimpleDate(new Date()), "some site"
                        ), expectedSecond,
                        new Offer(
                                4, "Offer4", "JavaOffer",
                                new SimpleDate(new Date()), "some site"
                        ), expectedThird
                ), this.store
        ).iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(expectedFirst));
        assertThat(iterator.next(), is(expectedSecond));
        assertThat(iterator.next(), is(expectedThird));
        assertThat(iterator.hasNext(), is(false));
    }

}