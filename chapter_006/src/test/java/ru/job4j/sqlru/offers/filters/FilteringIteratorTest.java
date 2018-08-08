package ru.job4j.sqlru.offers.filters;

import org.junit.Test;
import ru.job4j.sqlru.utils.SimpleDate;
import ru.job4j.sqlru.offers.Offer;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FilteringIteratorTest {

    @Test
    public void whenEmptyOffersIteratorThenHasNotNextElement() {
        Iterator<Offer> iterator = new FilteringIterator<>(
                new ArrayList<Offer>().iterator(), offerBlank -> true
        );
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOffersDoesNotSatisfyTheFilterThenHasNotNextElement() {
        Iterator<Offer> iterator = new FilteringIterator<>(
                Arrays.asList(
                        new Offer(
                                1, "Offer", "JavaOffer",
                                new SimpleDate(new Date()), "some site"
                        ), new Offer(
                                2, "Offer2", "JavaOffer",
                                new SimpleDate(new Date()), "some site"
                        )
                ).iterator(), offerBlank -> false
        );
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void whenOffersSatisfyTheFilterThenIteratorReturnThisOffers() {
        Offer expectedFirst = new Offer(
                2, "Offer2", "JavaOffer",
                new SimpleDate(new Date()), "some site"
        );
        Offer expectedSecond = new Offer(
                4, "Offer4", "JavaOffer",
                new SimpleDate(new Date()), "some site"
        );
        Iterator<Offer> iterator = new FilteringIterator<>(
                Arrays.asList(
                        new Offer(
                                1, "Offer1", "JavaOffer",
                                new SimpleDate(new Date()), "some site"
                        ), expectedFirst,
                        new Offer(
                                3, "Offer3", "JavaOffer",
                                new SimpleDate(new Date()), "some site"
                        ), expectedSecond,
                        new Offer(
                                5, "Offer5", "JavaOffer",
                                new SimpleDate(new Date()), "some site"
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
        new FilteringIterator<>(
                new ArrayList<Offer>().iterator(), offerBlank -> true
        ).next();
    }
}