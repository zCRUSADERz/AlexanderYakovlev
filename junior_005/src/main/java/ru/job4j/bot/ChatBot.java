package ru.job4j.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Chat bot.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public final class ChatBot {
    private final BufferedReader reader;
    private final PrintWriter writer;
    /**
     * If stopped==true then exit.
     */
    private final AtomicBoolean stopped;
    private final Map<String, Command> commands;
    private final Command defaultCommand;

    public ChatBot(final BufferedReader reader,
                   final PrintWriter writer,
                   final AtomicBoolean stopped,
                   final Map<String, Command> commands,
                   final Command defaultCommand) {
        this.reader = reader;
        this.writer = writer;
        this.stopped = stopped;
        this.commands = commands;
        this.defaultCommand = defaultCommand;
    }

    /**
     * Запуск чат-бота. Будет отвечать на любые команды(строки) пользователя,
     * пока this.stopped == false.
     */
    public final void start() {
        try {
            this.writer.println("Давай пообщаемся!");
            while (!this.stopped.get()) {
                final String question = reader.readLine();
                final Command command = this.commands.getOrDefault(
                        question.toLowerCase(), this.defaultCommand
                );
                command.execute();
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
