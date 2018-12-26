package ru.job4j;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * String service test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.12.2018
 */
public class StringServiceTest {
    private final StringService service = new StringService();

    @Test
    public void whenInputStreamWithoutAbuseWord() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.service.dropAbuses(
                new ByteArrayInputStream("Hello world!".getBytes()),
                out,
                new String[0]
        );
        final String expected = "Hello world!";
        assertEquals(expected, new String(out.toByteArray()));
    }

    @Test
    public void whenDropSomeAbuseWords() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.service.dropAbuses(
                new ByteArrayInputStream("Hello world! Aaaargh. aarg".getBytes()),
                out,
                new String[] {"o", "o w", "aargh"}
        );
        final String expected = "Hellrld! Aa. aarg";
        assertEquals(expected, new String(out.toByteArray()));
    }
}