package ru.job4j.oraclebot.oracle;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Remote oracle, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public class OracleServerTest {

    @Test
    public void theReturnedAnswerWithoutEmptyLine() {
        final String answer = "Answer";
        final OracleServer oracle = new OracleServer(
                new ByteArrayInputStream(
                        String.format("%s%n%n", answer).getBytes()
                ),
                new ByteArrayOutputStream()
        );
        assertThat(
                oracle.answer(""),
                is(String.format("%s%n", answer))
        );
    }

    @Test
    public void questionWasSentToOutputStream() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final OracleServer oracle = new OracleServer(
                new ByteArrayInputStream(
                        String.format("%n").getBytes()
                ),
                out
        );
        final String question = "Question";
        oracle.answer(question);
        assertThat(
                out.toString(),
                is(String.format("%s%n", question))
        );
    }

    @Test
    public void whenAnswerEmptyLineThenReturnEmptyLineWithLineSeparator() {
        final OracleServer oracle = new OracleServer(
                new ByteArrayInputStream(
                        System.lineSeparator().getBytes()
                ),
                new ByteArrayOutputStream()
        );
        assertThat(
                oracle.answer(""),
                is(System.lineSeparator())
        );
    }
}