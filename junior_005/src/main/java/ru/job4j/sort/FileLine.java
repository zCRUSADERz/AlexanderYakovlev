package ru.job4j.sort;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.function.Function;

/**
 * Line in file.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public final class FileLine {
    private final Path path;
    private final Function<Path, RandomAccessFile> filesAccess;
    private final long position;
    private final int bytes;
    private final Charset charset;

    public FileLine(final Path path,
                    final Function<Path, RandomAccessFile> filesAccess,
                    final long position, final int bytes, final Charset charset) {
        this.path = path;
        this.filesAccess = filesAccess;
        this.position = position;
        this.bytes = bytes;
        this.charset = charset;
    }

    /**
     * Return line bytes from file.
     * @return bytes from file.
     */
    public final byte[] bytes() {
        try {
            final RandomAccessFile access = this.filesAccess.apply(this.path);
            access.seek(this.position);
            final byte[] buffer = new byte[this.bytes];
            if (access.read(buffer) == -1) {
                throw new IllegalStateException("Line not exist in file.");
            }
            return buffer;
        } catch (IOException ex) {
            throw new IllegalStateException("File access error.", ex);
        }
    }

    /**
     * Return line from file.
     * @return line from file.
     */
    public final String line() {
        return new String(this.bytes(), this.charset);
    }

    /**
     * Copy line to OutputStream.
     * @param to copy line bytes to OutputStream.
     */
    public final void copy(final OutputStream to) throws IOException {
        to.write(this.bytes());

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FileLine fileLine = (FileLine) o;
        if (position != fileLine.position) {
            return false;
        }
        if (bytes != fileLine.bytes) {
            return false;
        }
        if (!path.equals(fileLine.path)) {
            return false;
        }
        return charset.equals(fileLine.charset);
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + (int) (position ^ (position >>> 32));
        result = 31 * result + bytes;
        result = 31 * result + charset.hashCode();
        return result;
    }
}
