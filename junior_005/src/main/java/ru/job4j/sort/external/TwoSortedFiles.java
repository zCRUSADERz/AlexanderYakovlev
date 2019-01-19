package ru.job4j.sort.external;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Objects;

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
    public final void merge(final Path dest) throws IOException {
        try (final BufferedReader readerFirst
                     = Files.newBufferedReader(this.firstFile);
             final BufferedReader readerSecond
                     = Files.newBufferedReader(this.secondFile);
             final BufferedWriter writer
                     = Files.newBufferedWriter(dest, StandardOpenOption.WRITE)) {
            String leftLine = readerFirst.readLine();
            String rightLine = readerSecond.readLine();
            while (!(Objects.isNull(leftLine) && Objects.isNull(rightLine))) {
                if (Objects.nonNull(leftLine)
                        && (Objects.isNull(rightLine)
                        || (this.lineComparator.compare(leftLine, rightLine) <= 0))) {
                    writer.write(leftLine);
                    writer.newLine();
                    leftLine = readerFirst.readLine();
                } else {
                    writer.write(rightLine);
                    writer.newLine();
                    rightLine = readerSecond.readLine();
                }
            }
        }
    }
}
