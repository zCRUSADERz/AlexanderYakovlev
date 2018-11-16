package ru.job4j.broker.issuers;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тестирование списка все эмитентов.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 09.03.2018
 */
public class IssuerListTest {

    @Test
    public void whenIssuerNotCreatedThanRegistrarReturnNew() {
        IssuerList issuerList = new IssuerList();
        GlassPriceLevel result = issuerList.registrar("Google", 10456);
        assertNotNull(result);
    }

    @Test
    public void whenOrderRegistrarNotCreatedThenRegistrarReturnNew() {
        IssuerList issuerList = new IssuerList();
        issuerList.registrar("Yahoo", 105);
        GlassPriceLevel result = issuerList.registrar("Yahoo", 1045);
        assertNotNull(result);
    }

    @Test
    public void whenOrderRegistrarExistThenRegistrarReturnThis() {
        IssuerList issuerList = new IssuerList();
        GlassPriceLevel expected = issuerList.registrar("Yandex", 574);
        GlassPriceLevel result = issuerList.registrar("Yandex", 574);
        assertThat(result, is(expected));
    }
}