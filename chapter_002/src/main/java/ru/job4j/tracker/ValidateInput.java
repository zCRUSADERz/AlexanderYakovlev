package ru.job4j.tracker;

/**
 * Validate input.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 6.01.2017
 * @version 1.0
 */
public class ValidateInput extends ConsoleInput {

    /**
     * Ask user valid answer.
     * @param question - question.
     * @param range - range of key menu.
     * @return - key menu.
     */
    @Override
    public int ask(String question, int[] range) {
        int value = -1;
        boolean invalid = true;
        do {
            try {
                value = super.ask(question, range);
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
