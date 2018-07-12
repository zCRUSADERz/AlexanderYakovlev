package ru.job4j.tracker;

/**
 * Base user action.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 6.01.2017
 * @version 1.0
 */
public abstract class BaseAction implements UserAction {
    private final int key;
    private final String name;

    BaseAction(int key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public abstract void execute(Input input, Tracker tracker) throws DBException;

    @Override
    public String info() {
        return String.format("%s. %s.", key, name);
    }
}
