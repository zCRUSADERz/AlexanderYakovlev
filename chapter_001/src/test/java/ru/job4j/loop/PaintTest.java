package ru.job4j.loop;

import org.junit.Test;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Paint test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 1.01.2018
 * @version 1.0
 */
public class PaintTest {

    @Test
    public void whenPyramidHeightFour() {
        Paint paint = new Paint();
        String result = paint.pyramid(4);
        String expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("   ^   ")
                .add("  ^^^  ")
                .add(" ^^^^^ ")
                .add("^^^^^^^")
                .toString();
        assertThat(result, is(expected));
    }
}
