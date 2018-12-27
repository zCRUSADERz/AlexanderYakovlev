package ru.job4j.sort;

import java.io.*;
import java.nio.file.Path;

/**
 * Line in file.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public final class FileLine implements Comparable<FileLine> {
    private final Path filePath;
    private final long pos;
    private final int bytes;

    public FileLine(final Path filePath, final long pos, final int bytes) {
        this.filePath = filePath;
        this.pos = pos;
        this.bytes = bytes;
    }

    /**
     * Write line to OutputStream.
     * @param to OutputStream in which write.
     */
    public final void write(OutputStream to) {
        try (final RandomAccessFile access
                     = new RandomAccessFile(this.filePath.toFile(), "r")) {
            access.seek(this.pos);
            final byte[] buffer = new byte[this.bytes];
            if (access.read(buffer) == -1) {
                throw new IllegalStateException(
                        "Impossible to read bytes because end of this "
                                + "file has been reached"
                );
            }
            to.write(buffer);
            to.write(System.lineSeparator().getBytes());
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("File not found", ex);
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Exception while working with the file", ex
            );
        }
    }

    @Override
    public final int compareTo(final FileLine o) {
        final int result;
        if (this.filePath.equals(o.filePath)) {
            result = Integer.compare(this.bytes, o.bytes);
        } else {
            result = -1;
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
        if (bytes != fileLine.bytes) {
            return false;
        }
        return filePath.equals(fileLine.filePath);
    }

    @Override
    public final int hashCode() {
        int result = filePath.hashCode();
        result = 31 * result + (int) (pos ^ (pos >>> 32));
        result = 31 * result + bytes;
        return result;
    }
}
