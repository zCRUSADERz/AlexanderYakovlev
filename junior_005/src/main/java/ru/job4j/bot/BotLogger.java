package ru.job4j.bot;

import org.apache.log4j.Logger;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Bot logger.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public final class BotLogger extends PrintWriter {
    private final Logger logger = Logger.getLogger(BotLogger.class);

    public BotLogger(final OutputStream out) {
        super(out, true);
    }

    /**
     * Логирует все ответы чат-бота.
     * @param answer ответ чат-бота.
     */
    @Override
    public final void println(final String answer) {
        this.logger.info(String.format("Bot: %s", answer));
        super.println(answer);
    }
}
