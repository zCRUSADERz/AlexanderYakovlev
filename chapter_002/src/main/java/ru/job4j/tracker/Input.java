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
}
