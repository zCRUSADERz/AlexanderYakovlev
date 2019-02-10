package ru.job4j.oraclebot.oracle;

import java.io.*;

/**
 * Remote oracle.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public final class OracleServer implements Oracle {
    /**
     * Socket output.
     */
    private final PrintWriter out;
    /**
     * Socket input.
     */
    private final BufferedReader in;

    public OracleServer(final InputStream input, final OutputStream output) {
        this(
                new PrintWriter(output, true),
                new BufferedReader(new InputStreamReader(input))
        );
    }

    public OracleServer(final PrintWriter out, final BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    /**
     * Ответ оркакула на заданный вопрос.
     * @param question вопрос.
     * @return ответ на вопрос.
     */
    @Override
    public final String answer(final String question) {
        try {
            this.out.println(question);
            final StringBuilder answer = new StringBuilder();
            String line = in.readLine();
            while (!line.isEmpty()) {
                answer.append(line).append(System.lineSeparator());
                line = in.readLine();
            }
            if (answer.length() == 0) {
                answer.append(System.lineSeparator());
            }
            return answer.toString();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
