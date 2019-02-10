package ru.job4j.oraclebot.oracle;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Oracle.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public final class OracleImpl implements Oracle {
    /**
     * Словарь вопрос->ответ.
     */
    private final Map<String, Iterable<String>> answers;
    /**
     * Ответ на неизвестный Оракулу вопрос.
     */
    private final Iterable<String> defaultAnswer;

    public OracleImpl() {
        this(
                Map.of(
                        "hello",
                        Collections.singleton(
                                "Hello, dear friend, I'm a oracle."
                        ),
                        "How are you?",
                        Arrays.asList(
                                "I'm fine",
                                "What about you?"
                        ),
                        "exit",
                        Collections.singleton("Goodbye!")
                ),
                Arrays.asList(
                        "Sorry, i do not know the answer to your question.",
                        "Ask something else."
                )
        );
    }

    public OracleImpl(final Map<String, Iterable<String>> answers,
                      final Iterable<String> defaultAnswer) {
        this.answers = answers;
        this.defaultAnswer = defaultAnswer;
    }

    /**
     * Ответ оркакула на заданный вопрос.
     * @param question вопрос.
     * @return ответ на вопрос.
     */
    @Override
    public final String answer(final String question) {
        final Iterable<String> answer
                = this.answers.getOrDefault(question, this.defaultAnswer);
        final StringBuilder builder = new StringBuilder();
        for (String line : answer) {
            builder.append(line).append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }
}
