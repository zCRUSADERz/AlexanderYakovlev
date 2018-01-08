package ru.job4j.pseudo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Paint test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.01.2017
 * @version 1.0
 */
public class PaintTest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOutput() {
        System.setOut(stdout);
    }

    @Test
    public void whenDrawSquare() {
        new Paint().draw(new Square());
        assertThat(
                new String(out.toByteArray()),
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

    @Test
    public void whenDrawTriangle() {
        new Paint().draw(new Triangle());
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(String.format("    .%n"))
                                .append(String.format("   /|%n"))
                                .append(String.format("  / |%n"))
                                .append(String.format(" /  |%n"))
                                .append(String.format("/___|%n"))
                                .toString()
                )
        );
    }
}