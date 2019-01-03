package ru.job4j.bot;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * User logger.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public final class UserLogger extends BufferedReader {
    private final Logger logger = Logger.getLogger(UserLogger.class);

    public UserLogger(final Reader in) {
        super(in);
    }

    /**
     * Логирует все что пишет пользователь боту.
     * @return строка пользователя.
     * @throws IOException IOException.
     */
    @Override
    public final String readLine() throws IOException {
        final String userQuestion = super.readLine();
        this.logger.info(String.format("User: %s", userQuestion));
        return userQuestion;
    }
}
