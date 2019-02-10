package ru.job4j.oraclebot.oracle;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Oracle bot, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public class OracleImplTest {

    @Test
    public void oracleResponseEndsInABlankLine() {
        final String answer = "Goodbye!";
        final OracleImpl oracle = new OracleImpl(
                Map.of("exit", Collections.singleton(answer)),
                Collections.singleton("")
        );
        assertThat(
                oracle.answer("exit"),
                is(String.format("%s%n%n", answer))
        );
    }

    @Test
    public void whenOracleDontKnowAnswerThenReturnDefaultAnswer() {
        final String answer = "I dont know answer";
        final OracleImpl oracle = new OracleImpl(
                Map.of(),
                Collections.singleton(answer)
        );
        assertThat(
                oracle.answer("Question"),
                is(String.format("%s%n%n", answer))
        );
    }

    @Test
    public void whenOracleKnowsTheAnswerThenReturnIt() {
        final String answer = "Some answer";
        final String question = "Some question";
        final OracleImpl oracle = new OracleImpl(
                Map.of(question, Collections.singleton(answer)),
                Collections.singleton("")
        );
        assertThat(
                oracle.answer(question),
                is(String.format("%s%n%n", answer))
        );
    }

    @Test
    public void oracleAnswerIncludesAllAnswerLinesWithLineSeparators() {
        final String answer1 = "First line answer";
        final String answer2 = "Second line answer";
        final String question = "Question";
        final OracleImpl oracle = new OracleImpl(
                Map.of(question, Arrays.asList(answer1, answer2)),
                Collections.singleton("")
        );
        assertThat(
                oracle.answer(question),
                is(String.format("%s%n%s%n%n", answer1, answer2))
        );
    }
}