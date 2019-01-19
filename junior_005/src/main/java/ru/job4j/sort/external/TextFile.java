package ru.job4j.sort.external;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
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
            new SortedFiles(tempFiles, lineComparator).multiWayMerge(dest);
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

    /**
     * PartFileIterator.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 18.01.2019
     */
    public final static class PartFileIterator implements Iterator<List<String>> {
        /**
         * Текстовый файл из которого читаем строки.
         */
        private final Path textFile;
        /**
         * Размер(в байтах) одного списка строк прочитанного из файла.
         */
        private final long partSize;
        /**
         * Позиция итератора в файле.
         */
        private long position;

        public PartFileIterator(final Path textFile, final long partSize) {
            this.textFile = textFile;
            this.partSize = partSize;
        }

        @Override
        public final boolean hasNext() {
            try {
                boolean result = true;
                if (Files.size(this.textFile) == this.position) {
                    result = false;
                }
                return result;
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }

        /**
         * Читает следующий список строк из файла размером в partSize байт.
         * @return список строк из файла размером в partSize.
         */
        @Override
        public final List<String> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            try (final RandomAccessFile raf = new RandomAccessFile(this.textFile.toFile(), "r")) {
                raf.seek(this.position);
                final List<String> lines = new ArrayList<>();
                while ((raf.getFilePointer() - this.position) < this.partSize) {
                    final String line = raf.readLine();
                    if (Objects.isNull(line)) {
                        break;
                    }
                    lines.add(new String(
                            line.getBytes(StandardCharsets.ISO_8859_1),
                            StandardCharsets.UTF_8
                    ));
                }
                this.position = raf.getFilePointer();
                return lines;
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }
}
