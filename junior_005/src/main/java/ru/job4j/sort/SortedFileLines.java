package ru.job4j.sort;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

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
     * Write sorted lines to OutputStream.
     * @param out OutputStream.
     * @throws FileNotFoundException if file not found.
     */
    public final void write(OutputStream out) throws FileNotFoundException {
        try (final FileLineStream lineStream = new FileLineStream(this.filePath)) {
            final SortedSet<FileLine> lines = new TreeSet<>();
            Optional<FileLine> optFileLine = lineStream.next();
            while (optFileLine.isPresent()) {
                lines.add(optFileLine.get());
                optFileLine = lineStream.next();
            }
            lines.forEach(fileLine -> fileLine.write(out));
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Exception by file line stream close.", ex
            );
        }
    }
}
