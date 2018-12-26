package ru.job4j;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Number service test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.12.2018
 */
public class NumberServiceTest {
    private final NumberService service = new NumberService();

    @Test
    public void whenInputStreamContainEvenNumberThenTrue() throws IOException {
        assertTrue(this.service.isNumber(
                new ByteArrayInputStream("0".getBytes()))
        );
        assertTrue(this.service.isNumber(
                new ByteArrayInputStream("-2".getBytes()))
        );
        assertTrue(this.service.isNumber(
                new ByteArrayInputStream("+6".getBytes()))
        );
        assertTrue(this.service.isNumber(
                new ByteArrayInputStream("8".getBytes()))
        );
        assertTrue(this.service.isNumber(
                new ByteArrayInputStream("107845121848653648".getBytes()))
        );
        assertTrue(this.service.isNumber(
                new ByteArrayInputStream("-76489107845121848653648".getBytes()))
        );
    }

    @Test
    public void whenInputStreamDoesNotContainEvenNumberThenFalse()
            throws IOException {
        assertFalse(this.service.isNumber(
                new ByteArrayInputStream("+124\n 126".getBytes()))
        );
        assertFalse(this.service.isNumber(
                new ByteArrayInputStream("-1".getBytes()))
        );
        assertFalse(this.service.isNumber(
                new ByteArrayInputStream("+00248".getBytes()))
        );
        assertFalse(this.service.isNumber(
                new ByteArrayInputStream("Hello world!".getBytes()))
        );
        assertFalse(this.service.isNumber(
                new ByteArrayInputStream("-10348684151".getBytes()))
        );
    }

}