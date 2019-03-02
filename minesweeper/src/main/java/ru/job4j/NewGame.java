package ru.job4j;

/**
 * NewGame.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.03.2019
 */
public final class NewGame {
    /**
     * Задачи на выполнение при старте новой игры.
     */
    private final Iterable<Runnable> runnables;

    public NewGame(Iterable<Runnable> runnables) {
        this.runnables = runnables;
    }

    /**
     * Выполнит все задачи необходимые для старта новой игры.
     */
    public final void start() {
        this.runnables.forEach(Runnable::run);
    }
}
