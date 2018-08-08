package ru.job4j.sqlru.offers.filters;

import org.junit.Test;
import ru.job4j.sqlru.offers.Offer;
import ru.job4j.sqlru.utils.SimpleDate;
import ru.job4j.sqlru.offers.JavaOffer;
import ru.job4j.sqlru.offers.Offers;
import ru.job4j.sqlru.offers.SpecificOffer;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SpecificOffersTest {
    private final Offer blankFirst = new Offer(
            2, "Offer2", "JavaOffer",
            new SimpleDate(new Date()), "some site"
    );
    private final Offer blankSecond = new Offer(
            4, "Offer4", "JavaOffer",
            new SimpleDate(new Date()), "some site"
    );
    private final Iterable<Offer> iterable = Arrays.asList(
            new Offer(
                    1, "Offer1", "JavaOffer",
                    new SimpleDate(new Date()), "some site"
            ), blankFirst,
            new Offer(
                    3, "Offer3", "JavaOffer",
                    new SimpleDate(new Date()), "some site"
            ), blankSecond,
            new Offer(
                    5, "Offer5", "JavaOffer",
                    new SimpleDate(new Date()), "some site"
            )
    );
    private final Offers<SpecificOffer> offers = new Offers<SpecificOffer>() {

        @Override
        public boolean isOffer(Offer blank) {
            return blank.equals(blankFirst) || blank.equals(blankSecond);
        }

        @Override
        public SpecificOffer createOffer(int id, String title,
                                         String text, SimpleDate created, String url) {
            return new JavaOffer(id, title, text, created, url);
        }
    };


    @Test
    public void whenFewJavaOfferThenIteratorReturnThisOffers() {
        Iterator<SpecificOffer> iterator
                = new SpecificOffers<>(iterable, offers).iterator();
        SpecificOffer expectedFirst = blankFirst.processToOffer(offers);
        SpecificOffer expectedSecond = blankSecond.processToOffer(offers);
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(expectedFirst));
        assertThat(iterator.next(), is(expectedSecond));
        assertThat(iterator.hasNext(), is(false));
    }
}