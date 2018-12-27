package ru.job4j.sort;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;

/**
 * File line stream.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public final class FileLineStream implements AutoCloseable {
    private final static int LINE_SEPARATOR = '\n';
    private final Path path;
    private final InputStream in;
    private long previousPosition = 0;

    public FileLineStream(final Path path) throws FileNotFoundException {
        this.path = path;
        this.in = new FileInputStream(this.path.toFile());
    }

    /**
     * Return next FileLine from file. if the file is read completely,
     * it will return Optional.empty().
     * @return Optional.of(FileLine).
     */
    public final Optional<FileLine> next() {
        try {
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
                int separatorBytesSize = System.lineSeparator().getBytes().length;
                if (nextByte == LINE_SEPARATOR) {
                    bytes -= separatorBytesSize - 1;
                }
                result = Optional.of(new FileLine(
                        this.path, this.previousPosition, bytes
                ));
                this.previousPosition += bytes + separatorBytesSize;
            }
            return result;
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "File stream throw I/O exception.", ex
            );
        }
    }

    @Override
    public final void close() throws IOException {
        this.in.close();
    }
}
