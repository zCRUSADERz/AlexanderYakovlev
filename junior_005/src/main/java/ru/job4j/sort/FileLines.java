package ru.job4j.sort;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * All file lines.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public final class FileLines {
    private final Path path;
    private final Charset charset;
    private final int separatorBytes;
    private final Function<Path, RandomAccessFile> filesAccess;

    public FileLines(final Path path, final Charset charset,
                           final Function<Path, RandomAccessFile> filesAccess) {
        this.path = path;
        this.charset = charset;
        this.separatorBytes = System.lineSeparator().getBytes(charset).length;
        this.filesAccess = filesAccess;
    }

    /**
     * Return list of file lines.
     * @return list of file lines.
     */
    public final List<FileLine> lines() {
        try (final Stream<String> stream
                     = Files.lines(this.path, this.charset)) {
            final PositionsInFile<FileLine> positions = new PositionsInFile<>(
                    (line, position) ->
                            new FileLine(
                                    this.path, this.filesAccess, position,
                                    line.getBytes(this.charset).length, this.charset
                            ),
                    this.charset,
                    this.separatorBytes
            );
            return stream
                    .map(positions::next)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Return stream of line positions in file.
     *
     * This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open file is closed promptly
     * after the stream's operations have completed.
     *
     * @return stream of line positions in file.
     */
    public final Stream<LinePosition> positions() {
        try {
            final PositionsInFile<LinePosition> positions = new PositionsInFile<>(
                    (line, position) ->
                            new LinePosition(
                                    position,
                                    line.getBytes(this.charset).length,
                                    line.length()
                            ),
                    this.charset,
                    this.separatorBytes
            );
            return Files
                    .lines(this.path, this.charset)
                    .map(positions::next);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static final class PositionsInFile<T> {
        private final BiFunction<String, Long, T> func;
        private final Charset charset;
        private final int separatorBytes;
        private long previousPosition = 0;

        private PositionsInFile(final BiFunction<String, Long, T> func,
                                final Charset charset, final int separatorBytes) {
            this.func = func;
            this.charset = charset;
            this.separatorBytes = separatorBytes;
        }

        public T next(final String line) {
            final int lineBytes = line.getBytes(charset).length;
            final T result = this.func.apply(line, this.previousPosition);
            previousPosition += lineBytes + separatorBytes;
            return result;
        }
    }
}
