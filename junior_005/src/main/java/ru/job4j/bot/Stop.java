package ru.job4j.bot;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Stop command.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public class Stop implements Command {
    /**
     * Silence mode[on=true, off=false].
     */
    private final AtomicBoolean silence;
    private final PrintWriter writer;

    public Stop(final AtomicBoolean silence, final PrintWriter writer) {
        this.silence = silence;
        this.writer = writer;
    }

    /**
     * Выполнение этой команды включает режим тишины. Бот перестает отвечать.
     */
    @Override
    public final void execute() {
        if (!this.silence.get()) {
            this.silence.set(true);
            this.writer.println("Silent mode on!");
        }
    }
}
