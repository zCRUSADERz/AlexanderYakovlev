package ru.job4j.sqlru.offers.filters;

import org.junit.Test;
import ru.job4j.sqlru.offers.Offer;
import ru.job4j.sqlru.utils.SimpleDate;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class OffersCreatedNotEarlierThanTest {

    @Test
    public void whenOffersIterableEmptyThenResultIterableEmpty() {
        Iterable<Offer> filter = new OffersCreatedNotEarlierThan(
                new ArrayList<>(), new Date()
        );
        Iterator<Offer> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOneOfferCreatedEarlierThenResultIterableEmpty() {
        Iterable<Offer> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        new Offer(
                                1, "Offer", "JavaOffer",
                                new SimpleDate(new Date(1)), "some site"
                        )
                ), new Date(10));
        Iterator<Offer> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenFewOfferCreatedEarlierThenResultIterableEmpty() {
        Iterable<Offer> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        new Offer(
                                1, "Offer", "JavaOffer",
                                new SimpleDate(new Date(1)), "some site"
                        ), new Offer(
                                2, "Offer2", "JavaOffer",
                                new SimpleDate(new Date(2)), "some site"
                        ), new Offer(
                                3, "Offer3", "JavaOffer",
                                new SimpleDate(new Date(3)), "some site"
                        )
                ), new Date(10));
        Iterator<Offer> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOneOfferCreatedLaterThenIterableHaveThisOffer() {
        Offer expected = new Offer(
                1, "Offer1", "JavaOffer",
                new SimpleDate(new Date(15)), "some site"
        );
        Iterable<Offer> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        expected,
                        new Offer(
                                2, "Offer2", "JavaOffer",
                                new SimpleDate(new Date(1)), "some site"
                        ), new Offer(
                                3, "Offer3", "JavaOffer",
                                new SimpleDate(new Date(3)), "some site"
                        )
                ), new Date(10));
        Iterator<Offer> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(expected));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenFewOfferCreatedLaterThenIterableHaveThisOffer() {
        Offer expectedFirst = new Offer(
                1, "Offer1", "JavaOffer",
                new SimpleDate(new Date(20)), "some site"
        );
        Offer expectedSecond = new Offer(
                2, "Offer2", "JavaOffer",
                new SimpleDate(new Date(25)), "some site"
        );
        Iterable<Offer> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        expectedFirst,
                        expectedSecond,
                        new Offer(
                                3, "Offer3", "JavaOffer",
                                new SimpleDate(new Date(1)), "some site"
                        )
                ), new Date(15));
        Iterator<Offer> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(expectedFirst));
        assertThat(iterator.next(), is(expectedSecond));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOfferCreatedEarlierThenIteratorHasNotOtherOffer() {
        Iterable<Offer> filter = new OffersCreatedNotEarlierThan(
                Arrays.asList(
                        new Offer(
                                1, "Offer", "JavaOffer",
                                new SimpleDate(new Date(1)), "some site"
                        ), new Offer(
                                2, "Offer2", "JavaOffer",
                                new SimpleDate(new Date(12)), "some site"
                        ), new Offer(
                                3, "Offer3", "JavaOffer",
                                new SimpleDate(new Date(13)), "some site"
                        )
                ), new Date(10));
        Iterator<Offer> iterator = filter.iterator();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenIterableEmptyThenIteratorNextThrowNoSuchElementException() {
        Iterable<Offer> filter = new OffersCreatedNotEarlierThan(
                new ArrayList<>(), new Date()
        );
        filter.iterator().next();
    }
}