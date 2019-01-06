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
    private FileLinePositions<LinePosition> fileLines;
    private final Function<LinePosition, FileLine> mapFunc;
    private final byte[] lineSeparator;

    public SortedFileLines(final Path path, final Charset charset,
                     final Function<Path, RandomAccessFile> filesAccess) {
        this.fileLines = new FileLinePositions<>(
                path, charset,
                (line, position) -> new LinePosition(
                        position, line.getBytes(charset).length, line.length()
                )
        );
        this.mapFunc = linePosition -> new FileLine(
                path, filesAccess, linePosition.position,
                linePosition.bytes, charset
        );
        this.lineSeparator = System.lineSeparator().getBytes(charset);
    }

    /**
     * Copy sorted lines to OutputStream.
     * @param out OutputStream.
     */
    public final void copy(OutputStream out) {
        try (final Stream<LinePosition> stream
                     = this.fileLines.positions()) {
            stream
                    .sorted()
                    .map(this.mapFunc)
                    .forEachOrdered(fileLine -> {
                        try {
                            fileLine.copy(out);
                            out.write(this.lineSeparator);
                        } catch (IOException ex) {
                            throw new UncheckedIOException(ex);
                        }
                    });
        }
    }

    /**
     * Line position in bytes array.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 03.01.2019
     */
    public final static class LinePosition implements Comparable<LinePosition> {
        final long position;
        final int bytes;
        final int chars;

        public LinePosition(final long position, final int bytes,
                            final int chars) {
            this.position = position;
            this.bytes = bytes;
            this.chars = chars;
        }

        /**
         * Если эта строка имеет больше символов, то результат > 0.
         * Если количество символов одинаково, то сравнивается позиция - если
         * позиция раньше(меньше) то результат > 0.
         * @param o other LinePosition.
         * @return результат сравнения.
         */
        @Override
        public final int compareTo(final LinePosition o) {
            int result = Integer.compare(this.chars, o.chars);
            if (result == 0) {
                result = Long.compare(o.position, this.position);
            }
            return result;
        }
    }
}
