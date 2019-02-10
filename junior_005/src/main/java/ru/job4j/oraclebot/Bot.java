package ru.job4j.oraclebot;

import ru.job4j.oraclebot.oracle.Oracle;

import java.io.*;

/**
 * Bot.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public final class Bot implements Runnable {
    /**
     * Question producer.
     */
    private final BufferedReader in;
    private final Oracle oracle;
    /**
     * Answer consumer.
     */
    private final PrintWriter out;

    public Bot(final InputStream input, final Oracle oracle,
               final OutputStream output) {
        this(
                new BufferedReader(new InputStreamReader(input)),
                oracle,
                new PrintWriter(output, true)
        );
    }

    public Bot(final BufferedReader in, final Oracle oracle,
               final PrintWriter out) {
        this.in = in;
        this.oracle = oracle;
        this.out = out;
    }

    @Override
    public final void run() {
        try {
            String question;
            do {
                question = this.in.readLine();
                final String answer = this.oracle.answer(question);
                this.out.print(answer);
                this.out.flush();
            } while (!"exit".equals(question));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
