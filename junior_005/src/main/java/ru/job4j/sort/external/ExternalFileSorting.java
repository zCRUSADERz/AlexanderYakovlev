package ru.job4j.sort.external;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;

/**
 * External file sorting.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 17.01.2019
 */
public final class ExternalFileSorting {
    private final File source;
    /**
     * Путь к директории в которой будут
     * созданы временные файлы во время сортировки.
     */
    private final Path tempDirectoryPath;
    /**
     * Максимальный размер одной части файла.
     * Исходный файл разбивается на множество файлов этого размера.
     */
    private final long maxSize;
    private final Comparator<String> lineComparator;

    public ExternalFileSorting(final File source, final Path tempDirectoryPath,
                               final long maxSize) {
        this(
                source, tempDirectoryPath, maxSize,
                Comparator.comparingInt(String::length)
        );
    }

    public ExternalFileSorting(final File source, final Path tempDirectoryPath,
                               final long maxSize,
                               final Comparator<String> lineComparator) {
        this.source = source;
        this.maxSize = maxSize;
        this.tempDirectoryPath = tempDirectoryPath;
        this.lineComparator = lineComparator;
    }

    /**
     * Записать отсортированные строки в указанный файл.
     * @param dest файл для записи отсортированных строк.
     */
    public final void sortTo(final File dest) {
        final Path tempDir = split();
        try {
            File[] tempFiles = Objects.requireNonNull(tempDir.toFile().listFiles());
            while (tempFiles.length > 2) {
                int secondIndex = 1;
                while (secondIndex < tempFiles.length) {
                    merge(
                            tempFiles[secondIndex - 1].toPath(),
                            tempFiles[secondIndex].toPath(),
                            Files.createTempFile(
                                    tempDir, "sorted", ".txt"
                            )
                    );
                    secondIndex += 2;
                }
                tempFiles = Objects.requireNonNull(tempDir.toFile().listFiles());
            }
            Files.createFile(dest.toPath());
            merge(tempFiles[0].toPath(), tempFiles[1].toPath(), dest.toPath());
            tempDir.toFile().delete();
        } catch (IOException ex) {
            File[] tempFiles
                    = Objects.requireNonNull(tempDir.toFile().listFiles());
            for (File tempFile : tempFiles) {
                tempFile.delete();
            }
            tempDir.toFile().delete();
            throw new UncheckedIOException(ex);
        }
    }

    private Path split() {
        final Path tempDir;
        try {
            tempDir = Files.createTempDirectory(this.tempDirectoryPath, "sorted");
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format(
                            "Unable to create temp directory: %s.",
                            this.tempDirectoryPath
                    ), e
            );
        }
        try (final RandomAccessFile raf
                     = new RandomAccessFile(source, "r")) {
            final List<String> lines = new ArrayList<>();
            final long fileSize = raf.length();
            long lastPosition = 0;
            while (fileSize != raf.getFilePointer()) {
                while ((raf.getFilePointer() - lastPosition) < this.maxSize) {
                    final String line = raf.readLine();
                    if (Objects.isNull(line)) {
                        break;
                    }
                    lines.add(new String(line.getBytes(ISO_8859_1), UTF_8));
                }
                lastPosition = raf.getFilePointer();
                final Path tempFile = Files.createTempFile(
                        tempDir, "sorted", ".txt"
                );
                lines.sort(this.lineComparator);
                Files.write(tempFile, lines, UTF_8);
                lines.clear();
            }
        } catch (IOException e) {
            try {
                Files.delete(tempDir);
            } catch (IOException e1) {
                e1.addSuppressed(e);
                throw new UncheckedIOException(e1);
            }
            throw new UncheckedIOException(e);
        }
        return tempDir;
    }

    /**
     * Слияние двух отсортированных файлов в один.
     * Строки в новом фале будут так же отсортированны.
     * @param first первый файл для слияния.
     * @param second второй файл для слияния.
     * @param dest результат слияния.
     * @throws IOException если исключение было выброшенно при чтении исходных
     * файлов или записи в конечный файл.
     */
    private void merge(final Path first, final Path second,
                       final Path dest) throws IOException {
        new TwoSortedFiles(first, second, this.lineComparator).mergeToFile(dest);
        Files.delete(first);
        Files.delete(second);
    }
}
