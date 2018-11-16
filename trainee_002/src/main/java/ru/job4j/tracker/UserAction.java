package ru.job4j.tracker;

/**
 * User action interface.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 6.01.2017
 * @version 1.0
 */
public interface UserAction {

    /**
     * Key action.
     * @return - key.
     */
    int key();

    /**
     * Run user action.
     * @param input - user input.
     * @param tracker - tracker.
     */
    void execute(Input input, Tracker tracker) throws DBException;

    /**
     * Menu info.
     * @return - menu info.
     */
    String info();
}
