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

    void execute(Input input, Tracker tracker);

    String info();
}
