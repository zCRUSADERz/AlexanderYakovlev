package ru.job4j.oraclebot.oracle;

/**
 * Oracle.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public interface Oracle {

    /**
     * Ответ оркакула на заданный вопрос.
     * @param question вопрос.
     * @return ответ на вопрос.
     */
    String answer(final String question);
}
