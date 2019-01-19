package ru.job4j.sort.external;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Text file.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 18.01.2019
 */
public final class TextFile {
    private final Path sourceFile;

    public TextFile(final Path sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Сортировка строк текущего файла с использованием внешней сортировки.
     * @param dest конечный файл.
     * @param tempDir директория для временных файлов.
     * @param maxPartSize максимальный размер одной части от этого файла.
     * @param lineComparator comparator для сортировки трок.
     * @throws IOException IOException.
     */
    public final void externalSort(final Path dest, final Path tempDir,
                                   final long maxPartSize,
                                   final Comparator<String> lineComparator) throws IOException {
        final Collection<Path> tempFiles = new ArrayList<>();
        try {
            new SortedTextLists(this, maxPartSize, lineComparator)
                    .writeSeparately(lineList -> {
                        try {
                            final Path tempFile = Files.createTempFile(
                                    tempDir, "sorted", ".txt"
                            );
                            tempFiles.add(tempFile);
                            return tempFile;
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
            new SortedFiles(tempFiles, lineComparator).twoWayMerge(tempDir, dest);
        } finally {
            for (Path tempFile : tempFiles) {
                tempFile.toFile().delete();
            }
        }
    }

    /**
     * Разделить файл на части с размером partSize байтов. Части будут
     * разделены по разделителям строк - ни одна строка не будет разделена
     * в разные части.
     *
     * !!! Размер каждой части будет больше, либо равен partSize.
     *
     *
     * @param partSize размер одной части
     * @return Lazy Iterator, который при каждом вызове next(), будет читать из
     * файла строки в количестве равном или немного превышающем partSize.
     */
    public final Iterator<List<String>> splitByLines(final long partSize) {
        return new PartFileIterator(this.sourceFile, partSize);
    }
}
