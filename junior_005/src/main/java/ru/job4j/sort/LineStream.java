package ru.job4j.sort;

import java.io.*;
import java.util.Optional;

/**
 * File line stream.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public final class LineStream implements AutoCloseable {
    private final static int LINE_SEPARATOR = '\n';
    private final InputStream in;
    private long previousPosition = 0;

    public LineStream(final InputStream in) {
        this.in = in;
    }

    /**
     * Return next FileLine from file. If the file is read completely,
     * it will return Optional.empty().
     * @return Optional.of(FileLine).
     */
    public final Optional<FileLine> next() throws IOException {
        final Optional<FileLine> result;
        int bytes = 0;
        int nextByte = in.read();
        if (nextByte == -1) {
            result = Optional.empty();
        } else {
            while (nextByte != LINE_SEPARATOR && nextByte != -1) {
                bytes++;
                nextByte = in.read();
            }
            if (nextByte == LINE_SEPARATOR) {
                bytes++;
            }
            result = Optional.of(new FileLine(this.previousPosition, bytes));
            this.previousPosition += bytes;
        }
        return result;
    }

    @Override
    public final void close() throws IOException {
        this.in.close();
    }
}
