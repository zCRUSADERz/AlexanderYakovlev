package ru.job4j.sort;

import java.io.*;

/**
 * Line in file.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public final class FileLine implements Comparable<FileLine> {
    private final long pos;
    private final int bytes;

    public FileLine(final long pos, final int bytes) {
        this.pos = pos;
        this.bytes = bytes;
    }

    /**
     * Write line from file to OutputStream.
     * @param from file.
     * @param to OutputStream.
     * @throws IOException IOException.
     */
    public final void write(final RandomAccessFile from,
                            final OutputStream to) throws IOException {
        from.seek(this.pos);
        final byte[] buffer = new byte[this.bytes];
        from.read(buffer);
        to.write(buffer);
    }

    @Override
    public final int compareTo(final FileLine o) {
        int result = Long.compare(this.bytes, o.bytes);
        if (result == 0) {
            result = Long.compare(this.pos, o.pos);
        }
        return result;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FileLine fileLine = (FileLine) o;
        if (pos != fileLine.pos) {
            return false;
        }
        return bytes == fileLine.bytes;
    }

    @Override
    public final int hashCode() {
        int result = (int) (pos ^ (pos >>> 32));
        result = 31 * result + bytes;
        return result;
    }
}
