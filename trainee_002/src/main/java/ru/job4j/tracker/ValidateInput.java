package ru.job4j.tracker;

import java.util.List;

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

    /**
     * Constructor.
     * @param input - user input.
     */
    ValidateInput(Input input) {
        this.input = input;
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
                System.out.println(moe.getMessage());
            } catch (NumberFormatException nfe) {
                System.out.println("Введите корректное значение снова.");
            }
        } while (invalid);
        return value;
    }
}
