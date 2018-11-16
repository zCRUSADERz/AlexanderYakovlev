package ru.job4j.max;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Max test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.12.2017
 * @version 1.0
 */
public class MaxTest {

    /**
     * Test maximum of two numbers.
     */
    @Test
    public void whenFirstLessSecond() {
        Max maximum = new Max();
        int result = maximum.max(1, 2);
        assertThat(result, is(2));
    }

    /**
     * Test maximum of three numbers.
     */
    @Test
    public void whenFirstMoreSecondAndSecondMoreThird() {
        Max maximum = new Max();
        int result = maximum.max(3, 2, 1);
        assertThat(result, is(3));
    }
}
