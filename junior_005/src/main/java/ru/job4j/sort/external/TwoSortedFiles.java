package ru.job4j.sort.external;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Two sorted files.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 18.01.2019
 */
public final class TwoSortedFiles {
    private final Path firstFile;
    private final Path secondFile;
    private final Comparator<String> lineComparator;

    public TwoSortedFiles(final Path firstFile, final Path secondFile,
                          final Comparator<String> lineComparator) {
        this.firstFile = firstFile;
        this.secondFile = secondFile;
        this.lineComparator = lineComparator;
    }

    /**
     * Слияние файлов в один файл с сортировкой записываемых строк.
     * @param dest Path к файлу в который будут записаны строки.
     * @throws IOException если было выброшено исключение
     * во время чтения или записи файлов.
     */
    public final void mergeToFile(final Path dest) throws IOException {
        try (final Stream<String> firstStream = Files.lines(this.firstFile);
             final Stream<String> secondStream = Files.lines(this.secondFile)) {
            Files.write(
                    dest,
                    new TwoSortedIterables<>(
                            firstStream::iterator,
                            secondStream::iterator,
                            this.lineComparator
                    ));
        }
    }
}
