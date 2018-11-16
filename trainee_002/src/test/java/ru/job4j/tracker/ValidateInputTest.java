package ru.job4j.tracker;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;

/**
 * Validate input test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 3.01.2017
 * @version 1.0
 */
public class ValidateInputTest {
    private final List<String> buffer = new ArrayList<>();

    @After
    public void clearBuffer() {
        this.buffer.clear();
    }

    @Test
    public void whenInvalidInput() {
        ValidateInput input = this.input("invalid", "1");
        input.ask("Enter", Arrays.asList(1, 2));
        assertThat(
                this.buffer.get(0),
               is("Введите корректное значение снова.")
        );
        assertThat(this.buffer, iterableWithSize(1));
    }

    @Test
    public void whenOutOfRangeMenu() {
        ValidateInput input = this.input("-1", "1");
        input.ask("", Arrays.asList(1, 2));
        assertThat(
                this.buffer.get(0),
                is("Выберите значение из диапазона меню.")
        );
        assertThat(this.buffer, iterableWithSize(1));
    }

    private ValidateInput input(String... answers) {
        return new ValidateInput(
                new InputFromSupplier(
                        new LinkedList<>(Arrays.asList(answers))::poll,
                        System.out::println
                ),
                this.buffer::add
        );
    }
}