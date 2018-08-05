package ru.job4j.sqlru.offers.filters;

import org.junit.Test;
import ru.job4j.sqlru.offers.OfferBlank;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class OffersFilteringIteratorTest {

    @Test
    public void whenEmptyOffersIteratorThenHasNotNextElement() {
        Iterator<OfferBlank> iterator = new OffersFilteringIterator(
                new ArrayList<OfferBlank>().iterator(), offerBlank -> true
        );
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOffersDoesNotSatisfyTheFilterThenHasNotNextElement() {
        Iterator<OfferBlank> iterator = new OffersFilteringIterator(
                Arrays.asList(
                        new OfferBlank(
                                1, "Offer",
                                "JavaOffer", new Date(), "some site"
                        ), new OfferBlank(
                                2, "Offer2",
                                "JavaOffer", new Date(), "some site"
                        )
                ).iterator(), offerBlank -> false
        );
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOffersSatisfyTheFilterThenIteratorReturnThisOffers() {
        OfferBlank expectedFirst = new OfferBlank(
                2, "Offer2",
                "JavaOffer", new Date(), "some site"
        );
        OfferBlank expectedSecond = new OfferBlank(
                4, "Offer4",
                "JavaOffer", new Date(), "some site"
        );
        Iterator<OfferBlank> iterator = new OffersFilteringIterator(
                Arrays.asList(
                        new OfferBlank(
                                1, "Offer1",
                                "JavaOffer", new Date(), "some site"
                        ), expectedFirst,
                        new OfferBlank(
                                3, "Offer3",
                                "JavaOffer", new Date(), "some site"
                        ), expectedSecond,
                        new OfferBlank(
                                5, "Offer5",
                                "JavaOffer", new Date(), "some site"
                        )
                ).iterator(),
                offerBlank ->
                        offerBlank.equals(expectedFirst)
                                || offerBlank.equals(expectedSecond)
        );
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(expectedFirst));
        assertThat(iterator.next(), is(expectedSecond));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenEmptyOffersIteratorThenIteratorNextThrowNoSuchElementException() {
        new OffersFilteringIterator(
                new ArrayList<OfferBlank>().iterator(), offerBlank -> true
        ).next();
    }
}