package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Validate input test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 * @version 1.0
 */
public class ValidateInputTest {
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
    public void whenInvalidInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[]{"invalid", "1"})
        );
        input.ask("Enter", Arrays.asList(1, 2));
        assertThat(
                out.toString(),
                is(
                        String.format("Enter%nВведите корректное значение снова.%nEnter%n")
                )
        );
    }

    @Test
    public void whenOutOfRangeMenu() {
        ByteArrayInputStream in = new ByteArrayInputStream(String.format("-1%n1").getBytes());
        ValidateInput input = new ValidateInput(new ConsoleInput(in));
        input.ask("", Arrays.asList(1, 2));
        assertThat(
                out.toString(),
                is(
                        String.format("Выберите значение из диапазона меню.%n")
                )
        );
    }
}