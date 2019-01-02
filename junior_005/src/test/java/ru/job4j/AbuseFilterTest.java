package ru.job4j;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Abuse filter. Test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.01.2019
 */
public class AbuseFilterTest {

    @Test
    public void whenDropAbuseThenTextWithoutAbuseWords() throws IOException {
        final AbuseFilter filter = new AbuseFilter();
        final String text = String.format(""
                + "Autumn leaves are falling down, %n"
                + "Falling down, falling down, %n"
                + "Autumn leaves are falling down, %n"
                + "Yellow, red, orange and brown!"
        );
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final InputStream in = new ByteArrayInputStream(text.getBytes())) {
            filter.dropAbuses(in, out, new String[] {"down", "leaves", "Autumn"});
            final String expected = String.format(""
                    + "  are falling , %n"
                    + "Falling , falling , %n"
                    + "  are falling , %n"
                    + "Yellow, red, orange and brown!%n"
            );
            assertEquals(expected, out.toString(StandardCharsets.UTF_8));
        }
    }
}