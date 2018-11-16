package ru.job4j.tracker;

import java.util.List;
import java.util.function.Consumer;

/**
 * Validate input.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 6.01.2017
 * @version 2.0
 */
public class ValidateInput implements Input {
    /**
     * User input.
     */
    private final Input input;
    private final Consumer<String> writer;

    /**
     * Constructor.
     * @param input - user input.
     */
    ValidateInput(Input input) {
        this(input, System.out::println);
    }

    ValidateInput(Input input, Consumer<String> writer) {
        this.input = input;
        this.writer = writer;
    }

    /**
     * Ask user.
     * @param question - question for user.
     * @return - answer.
     */
    @Override
    public String ask(String question) {
        return input.ask(question);
    }

    /**
     * Ask user valid answer.
     * @param question - question.
     * @param range - range of key menu.
     * @return - key menu.
     */
    @Override
    public int ask(String question, List<Integer> range) {
        int value = -1;
        boolean invalid = true;
        do {
            try {
                value = input.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                this.writer.accept(moe.getMessage());
            } catch (NumberFormatException nfe) {
                this.writer.accept("Введите корректное значение снова.");
            }
        } while (invalid);
        return value;
    }
}
