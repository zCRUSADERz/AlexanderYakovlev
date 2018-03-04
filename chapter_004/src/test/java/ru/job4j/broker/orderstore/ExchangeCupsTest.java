package ru.job4j.broker.orderstore;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Exchange orderstore test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2018
 */
public class ExchangeCupsTest {

    @Test
    public void whenGetNonExistentCupThenReturnNewCup() {
        ExchangeCups cups = new ExchangeCups();
        cups.orderStore("Google", 1);
        String result = cups.toString();
        String expected = String.format(
                "Эмитенты и их биржевые стаканы зарегистрированные в системе:%n"
                        + "Google%nПродажа Цена Покупка"
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetExistCupThenReturnThisCup() {
        ExchangeCups cups = new ExchangeCups();
        OrderStore expectedStore = cups.orderStore("Yandex", 1);
        OrderStore resultStore = cups.orderStore("Yandex", 1);
        assertThat(resultStore, is(expectedStore));
        String result = cups.toString();
        String expected = String.format(
                "Эмитенты и их биржевые стаканы зарегистрированные в системе:%n"
                        + "Yandex%nПродажа Цена Покупка");
        assertThat(result, is(expected));
    }
}