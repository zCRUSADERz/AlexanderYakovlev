package ru.job4j.sort;

import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
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
    private FileLinePositions<FileLine> fileLines;

    public FileLines(final Path path, final Charset charset,
                           final Function<Path, RandomAccessFile> filesAccess) {
        this.fileLines = new FileLinePositions<>(
                path, charset,
                (line, position) -> new FileLine(
                        path, filesAccess,
                        position, line.getBytes(charset).length, charset
                )
        );
    }

    /**
     * Return list of file lines.
     * @return list of file lines.
     */
    public final List<FileLine> lines() {
        try (final Stream<FileLine> stream
                     = this.fileLines.positions()) {
            return stream
                    .collect(Collectors.toList());
        }
    }
}
