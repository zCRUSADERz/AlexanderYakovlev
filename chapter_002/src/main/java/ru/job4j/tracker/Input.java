package ru.job4j.tracker;

/**
 * Input interface.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 1.0
 */
public interface Input {

    /**
     * Ask user.
     * @param question - question for user.
     * @return - answer.
     */
    String ask(String question);

    /**
     * Ask user valid answer.
     * @param question - question.
     * @param range - range of key menu.
     * @return - key menu.
     */
    int ask(String question, int[] range);
}
