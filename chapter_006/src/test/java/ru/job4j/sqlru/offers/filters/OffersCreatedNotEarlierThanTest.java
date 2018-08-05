package ru.job4j.sqlru.offers.filters;

import org.junit.Test;
import ru.job4j.sqlru.offers.OfferBlank;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class OffersCreatedNotEarlierThanTest {

    @Test
    public void whenOffersIterableEmptyThenResultIterableEmpty() {
        Iterable<OfferBlank> filter = new OffersCreatedNotEarlierThan(new ArrayList<>(), new Date());
        Iterator<OfferBlank> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOneOfferCreatedEarlierThenResultIterableEmpty() {
        Iterable<OfferBlank> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        new OfferBlank(
                                1, "Offer",
                                "JavaOffer", new Date(1), "some site"
                        )
                ), new Date(10));
        Iterator<OfferBlank> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenFewOfferCreatedEarlierThenResultIterableEmpty() {
        Iterable<OfferBlank> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        new OfferBlank(
                                1, "Offer",
                                "JavaOffer", new Date(1), "some site"
                        ), new OfferBlank(
                                2, "Offer2",
                                "JavaOffer", new Date(2), "some site"
                        ), new OfferBlank(
                                3, "Offer3",
                                "JavaOffer", new Date(3), "some site"
                        )
                ), new Date(10));
        Iterator<OfferBlank> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOneOfferCreatedLaterThenIterableHaveThisOffer() {
        OfferBlank expected = new OfferBlank(
                1, "Offer1",
                "JavaOffer", new Date(15), "some site"
        );
        Iterable<OfferBlank> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        expected,
                        new OfferBlank(
                                2, "Offer2",
                                "JavaOffer", new Date(1), "some site"
                        ), new OfferBlank(
                                3, "Offer3",
                                "JavaOffer", new Date(3), "some site"
                        )
                ), new Date(10));
        Iterator<OfferBlank> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(expected));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenFewOfferCreatedLaterThenIterableHaveThisOffer() {
        OfferBlank expectedFirst = new OfferBlank(
                1, "Offer1",
                "JavaOffer", new Date(20), "some site"
        );
        OfferBlank expectedSecond = new OfferBlank(
                2, "Offer2",
                "JavaOffer", new Date(25), "some site"
        );
        Iterable<OfferBlank> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        expectedFirst,
                        expectedSecond,
                        new OfferBlank(
                                3, "Offer3",
                                "JavaOffer", new Date(1), "some site"
                        )
                ), new Date(15));
        Iterator<OfferBlank> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(expectedFirst));
        assertThat(iterator.next(), is(expectedSecond));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOfferCreatedEarlierThenIteratorHasNotOtherOffer() {
        Iterable<OfferBlank> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        new OfferBlank(
                                1, "Offer",
                                "JavaOffer", new Date(1), "some site"
                        ), new OfferBlank(
                                2, "Offer2",
                                "JavaOffer", new Date(12), "some site"
                        ), new OfferBlank(
                                3, "Offer3",
                                "JavaOffer", new Date(13), "some site"
                        )
                ), new Date(10));
        Iterator<OfferBlank> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenIterableEmptyThenIteratorNextThrowNoSuchElementException() {
        Iterable<OfferBlank> filter = new OffersCreatedNotEarlierThan(new ArrayList<>(), new Date());
        filter.iterator().next();
    }
}