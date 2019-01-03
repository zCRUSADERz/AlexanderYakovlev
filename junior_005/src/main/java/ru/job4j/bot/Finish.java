package ru.job4j.bot;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Finish command.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public class Finish implements Command {
    /**
     * If stopped==true then exit.
     */
    private final AtomicBoolean stopped;
    private final PrintWriter writer;

    public Finish(final AtomicBoolean stopped, final PrintWriter writer) {
        this.stopped = stopped;
        this.writer = writer;
    }

    /**
     * Останавливает выполнение программы чат-бот.
     */
    @Override
    public void execute() {
        this.stopped.set(true);
        this.writer.println("Bye, bye!");
    }
}
