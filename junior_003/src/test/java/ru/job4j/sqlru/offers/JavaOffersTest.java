package ru.job4j.sqlru.offers;

import org.junit.Test;
import ru.job4j.sqlru.utils.SimpleDate;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JavaOffersTest {

    @Test
    public void whenTitleNotContainsJavaThenIsOfferReturnFalse() {
        JavaOffers offers = new JavaOffers();
        Offer blank = new Offer(1, "Offer",
                "description", new SimpleDate(new Date()), "url"
        );
        assertThat(offers.isOffer(blank), is(false));
    }

    @Test
    public void whenTitleContainsJavaScriptThenIsOfferReturnTrue() {
        JavaOffers offers = new JavaOffers();
        Offer blank = new Offer(1, "JavaScript Offer",
                "description", new SimpleDate(new Date()), "url"
        );
        assertThat(offers.isOffer(blank), is(false));
    }

    @Test
    public void whenTitleContainsJavaSpaceScriptThenIsOfferReturnTrue() {
        JavaOffers offers = new JavaOffers();
        Offer blank = new Offer(1, "Java Script Offer",
                "description", new SimpleDate(new Date()), "url"
        );
        assertThat(offers.isOffer(blank), is(false));
    }

    @Test
    public void whenTitleContainsJavaThenIsOfferReturnTrue() {
        JavaOffers offers = new JavaOffers();
        Offer blank = new Offer(1, "Java Offer",
                "description", new SimpleDate(new Date()), "url"
        );
        assertThat(offers.isOffer(blank), is(true));
    }

    @Test
    public void whenTitleContainsJavaAndJavaScriptThenIsOfferReturnTrue() {
        JavaOffers offers = new JavaOffers();
        Offer blank = new Offer(1, "Java/JavaScript Offer",
                "description", new SimpleDate(new Date()), "url"
        );
        assertThat(offers.isOffer(blank), is(true));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenOfferIsNotJavaThenCreateOfferThrowIllegalArgumentException() {
        new JavaOffers().createOffer(
                1, "Offer", "description",
                new SimpleDate(new Date()), "url"
        );
    }
}