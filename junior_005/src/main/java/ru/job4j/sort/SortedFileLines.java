package ru.job4j.sort;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Sorted file next.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public final class SortedFileLines {
    private final Path path;
    private final Charset charset;
    private final String lineSeparator;
    private final byte[] lineSeparatorBytes;
    private final Function<Path, RandomAccessFile> filesAccess;

    public SortedFileLines(final Path path, final Charset charset,
                           final String lineSeparator,
                           final Function<Path, RandomAccessFile> filesAccess) {
        this.path = path;
        this.charset = charset;
        this.lineSeparator = lineSeparator;
        this.lineSeparatorBytes = lineSeparator.getBytes(charset);
        this.filesAccess = filesAccess;
    }

    /**
     * Copy sorted lines to OutputStream.
     * @param out OutputStream.
     */
    public final void copy(OutputStream out) {
        try (final Stream<LinePosition> positions
                     = new FileLines(this.path, this.charset,
                this.lineSeparator, filesAccess).positions()) {
            positions
                    .sorted()
                    .map(lineProperties ->
                            lineProperties.fileLine(path, filesAccess, charset))
                    .forEach(fileLine -> {
                        try {
                            fileLine.copy(out);
                            out.write(this.lineSeparatorBytes);
                        } catch (IOException ex) {
                            throw new UncheckedIOException(ex);
                        }
                    });
        }
    }
}
