package ru.job4j.sqlru.offers.filters;

import org.junit.Test;
import ru.job4j.sqlru.offers.JavaOffer;
import ru.job4j.sqlru.offers.OfferBlank;
import ru.job4j.sqlru.offers.Offers;
import ru.job4j.sqlru.offers.SpecificOffer;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SpecificOffersTest {
    private final OfferBlank blankFirst = new OfferBlank(
            2, "Offer2",
            "JavaOffer", new Date(), "some site"
    );
    private final OfferBlank blankSecond = new OfferBlank(
            4, "Offer4",
            "JavaOffer", new Date(), "some site"
    );
    private final Iterable<OfferBlank> iterable = Arrays.asList(
            new OfferBlank(
                    1, "Offer1",
                    "JavaOffer", new Date(), "some site"
            ), blankFirst,
            new OfferBlank(
                    3, "Offer3",
                    "JavaOffer", new Date(), "some site"
            ), blankSecond,
            new OfferBlank(
                    5, "Offer5",
                    "JavaOffer", new Date(), "some site"
            )
    );
    private final Offers<SpecificOffer> offers = new Offers<SpecificOffer>() {

        @Override
        public boolean isOffer(OfferBlank blank) {
            return blank.equals(blankFirst) || blank.equals(blankSecond);
        }

        @Override
        public SpecificOffer createOffer(int id, String title,
                                         String text, Date created, String url) {
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