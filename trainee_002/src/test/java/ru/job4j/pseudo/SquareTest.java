package ru.job4j.pseudo;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Paint test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.01.2017
 * @version 1.0
 */
public class SquareTest {

    @Test
    public void whenDrawSquare() {
        Square square = new Square();
        assertThat(
                square.draw(),
                is(
                        new StringBuilder()
                                .append(String.format(" _______ %n"))
                                .append(String.format("|       |%n"))
                                .append(String.format("|       |%n"))
                                .append(String.format("|       |%n"))
                                .append(String.format("|_______|%n"))
                                .toString()
                )
        );
    }
}
