package ru.job4j.sort;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Sorted file line.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public final class SortedFileLines {
    private final Path filePath;

    public SortedFileLines(final Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Copy sorted lines to OutputStream.
     * @param out OutputStream.
     */
    public final void copy(OutputStream out) {
        try (final LineStream lineStream
                     = new LineStream(
                new BufferedInputStream(
                        new FileInputStream(filePath.toFile())));
             final RandomAccessFile from
                     = new RandomAccessFile(filePath.toFile(), "r")) {
            Stream.iterate(
                    lineStream.next(),
                    Optional::isPresent,
                    fileLine -> {
                        try {
                            return lineStream.next();
                        } catch (IOException ex) {
                            throw new UncheckedIOException(ex);
                        }
                    }
            )
                    .map(Optional::get)
                    .sorted()
                    .forEach(fileLine -> {
                        try {
                            fileLine.write(from, out);
                        } catch (IOException ex) {
                            throw new UncheckedIOException(ex);
                        }
                    });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
