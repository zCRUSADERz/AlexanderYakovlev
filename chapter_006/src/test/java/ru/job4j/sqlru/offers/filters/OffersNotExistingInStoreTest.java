package ru.job4j.sqlru.offers.filters;

import org.junit.Test;
import ru.job4j.sqlru.offers.OfferBlank;
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
        public void save(int id, String title, String text, Date created, String url) {
        }

        @Override
        public void close() {
        }
    };

    @Test
    public void whenSomeOffersExistInStoreThenIterableHasNotThisOffers() {
        OfferBlank expectedFirst = new OfferBlank(
                1, "Offer1",
                "JavaOffer", new Date(), "some site"
        );
        OfferBlank expectedSecond = new OfferBlank(
                3, "Offer3",
                "JavaOffer", new Date(), "some site"
        );
        OfferBlank expectedThird = new OfferBlank(
                5, "Offer5",
                "JavaOffer", new Date(), "some site"
        );
        Iterator<OfferBlank> iterator = new OffersNotExistingInStore(
                Arrays.asList(
                        expectedFirst,
                        new OfferBlank(
                                2, "Offer2",
                                "JavaOffer", new Date(), "some site"
                        ), expectedSecond,
                        new OfferBlank(
                                4, "Offer4",
                                "JavaOffer", new Date(), "some site"
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