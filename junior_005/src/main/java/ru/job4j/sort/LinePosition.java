package ru.job4j.sort;

import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.function.Function;

/**
 * Line position in bytes array.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public final class LinePosition implements Comparable<LinePosition> {
    private final long position;
    private final int bytes;
    private final int chars;

    public LinePosition(final long position, final int bytes,
                        final int chars) {
        this.position = position;
        this.bytes = bytes;
        this.chars = chars;
    }

    /**
     * Convert to FileLine.
     * @param path file path.
     * @param filesAccess file access.
     * @param charset file charset.
     * @return new FileLine.
     */
    public final FileLine fileLine(
            final Path path, final Function<Path, RandomAccessFile> filesAccess,
            final Charset charset) {
        return new FileLine(path, filesAccess, this.position, this.bytes, charset);
    }

    /**
     * Если эта строка имеет больше символов, то результат > 0.
     * Если количество символов одинаково, то сравнивается позиция - если
     * позиция раньше(меньше) то результат > 0.
     * @param o other LinePosition.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(final LinePosition o) {
        int result = Integer.compare(this.chars, o.chars);
        if (result == 0) {
            result = Long.compare(o.position, this.position);
        }
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LinePosition that = (LinePosition) o;
        if (position != that.position) {
            return false;
        }
        if (bytes != that.bytes) {
            return false;
        }
        return chars == that.chars;
    }

    @Override
    public int hashCode() {
        int result = (int) (position ^ (position >>> 32));
        result = 31 * result + bytes;
        result = 31 * result + chars;
        return result;
    }
}
