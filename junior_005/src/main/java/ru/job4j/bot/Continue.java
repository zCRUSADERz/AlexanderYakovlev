package ru.job4j.bot;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Continue command.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public class Continue implements Command {
    /**
     * Silence mode[on=true, off=false].
     */
    private final AtomicBoolean silence;
    private final PrintWriter writer;

    public Continue(final AtomicBoolean silence, final PrintWriter writer) {
        this.silence = silence;
        this.writer = writer;
    }

    /**
     * Выполнение этой команды отключает режим тишины.
     */
    @Override
    public void execute() {
        if (this.silence.get()) {
            this.silence.set(false);
            this.writer.println("Silent mode off!");
        } else {
            this.writer.println("Silent mode has not been activated.");
        }
    }
}
