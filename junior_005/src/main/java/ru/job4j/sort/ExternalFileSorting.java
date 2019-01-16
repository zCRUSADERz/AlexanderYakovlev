package ru.job4j.sort;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * External file sorting.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 17.01.2019
 */
public final class ExternalFileSorting {
    private final File source;
    private final Path tempDirectoryPath;
    private final long maxSize;

    public ExternalFileSorting(final File source, final Path tempDirectoryPath,
                               final long maxSize) {
        this.source = source;
        this.maxSize = maxSize;
        this.tempDirectoryPath = tempDirectoryPath;
    }

    /**
     * Записать отсортированные строки в указанный файл.
     * @param dest файл для записи отсортированных строк.
     */
    public final void sortTo(final File dest) {
        final Path tempDir = split();
        try {
            File[] tempFiles = tempDir.toFile().listFiles();
            while (tempFiles.length > 2) {
                int secondIndex = 1;
                while (secondIndex < tempFiles.length) {
                    final Path tempFile = Files.createTempFile(
                            tempDir, "sorted", ".txt"
                    );
                    merge(tempFiles[secondIndex - 1], tempFiles[secondIndex], tempFile);
                    secondIndex += 2;
                }
                tempFiles = tempDir.toFile().listFiles();
            }
            Files.createFile(dest.toPath());
            merge(tempFiles[0], tempFiles[1], dest.toPath());
            tempDir.toFile().delete();
        } catch (IOException ex) {
            File[] tempFiles = tempDir.toFile().listFiles();
            if (Objects.nonNull(tempFiles)) {
                for (File tempFile : tempFiles) {
                    tempFile.delete();
                }
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
                    lines.add(new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }
                lastPosition = raf.getFilePointer();
                final Path tempFile = Files.createTempFile(
                        tempDir, "sorted", ".txt"
                );
                lines.sort(Comparator.comparingInt(String::length));
                Files.write(tempFile, lines, StandardCharsets.UTF_8);
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

    private void merge(final File first, final File second,
                       final Path dest) throws IOException {
        try (final BufferedReader readerFirst
                     = Files.newBufferedReader(first.toPath());
             final BufferedReader readerSecond
                     = Files.newBufferedReader(second.toPath());
             final BufferedWriter writer
                     = Files.newBufferedWriter(dest, StandardOpenOption.WRITE)) {
            String leftLine = readerFirst.readLine();
            String rightLine = readerSecond.readLine();
            while (!(Objects.isNull(leftLine) && Objects.isNull(rightLine))) {
                if (Objects.nonNull(leftLine)
                        && (Objects.isNull(rightLine)
                                || (leftLine.length() <= rightLine.length()))) {
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
        Files.delete(first.toPath());
        Files.delete(second.toPath());
    }
}
