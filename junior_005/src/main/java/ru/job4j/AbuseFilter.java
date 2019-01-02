package ru.job4j;

import java.io.*;
import java.util.Arrays;

/**
 * Abuse filter.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.01.2019
 */
public class AbuseFilter {

    /**
     * Delete all abuse word from InputStream.
     * @param in input stream.
     * @param out output stream.
     * @param abuse abuse words.
     * @throws UncheckedIOException if input or output streams throws IOException.
     */
    void dropAbuses(final InputStream in, final OutputStream out,
                    final String[] abuse) throws IOException {
        try (final PrintStream writer = new PrintStream(out);
             final BufferedReader reader
                     = new BufferedReader(new InputStreamReader(in))) {
                reader.lines()
                    .map(s -> Arrays.stream(abuse)
                            .reduce(s, (s1, s2) -> s1.replaceAll(s2, ""))
                    ).forEach(writer::println);
        }
    }

}
