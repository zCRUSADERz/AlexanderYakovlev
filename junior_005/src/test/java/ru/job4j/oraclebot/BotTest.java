package ru.job4j.oraclebot;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Bot, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public class BotTest {
    private final String exit = "exit";

    @Test
    public void whenEnterNewQuestionThenBotAskOracleAndReturnAnswer() {
        final String question = "Question";
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Bot bot = new Bot(
                new ByteArrayInputStream(
                        String.format("%s%n%s%n", question, this.exit).getBytes()
                ),
                (userQuestion) -> userQuestion,
                out
        );
        bot.run();
        assertThat(
                out.toString(),
                is(String.format("%s%s", question, this.exit))
        );
    }
}