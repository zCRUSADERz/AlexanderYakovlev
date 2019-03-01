package ru.job4j;

public final class NewGame {
    private final Iterable<Runnable> runnables;

    public NewGame(Iterable<Runnable> runnables) {
        this.runnables = runnables;
    }

    public final void start() {
        this.runnables.forEach(Runnable::run);
    }
}
