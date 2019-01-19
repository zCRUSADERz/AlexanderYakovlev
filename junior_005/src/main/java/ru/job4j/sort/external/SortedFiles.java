package ru.job4j.sort.external;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Sorted files.
 * Объект представляет коллекцию отсортированных файлов с текстом.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 18.01.2019
 */
public final class SortedFiles {
    /**
     * Коллекция путей к отсортированным файлам.
     */
    private final Collection<Path> sortedFiles;
    /**
     * Comparator с помощью которого были отсортированны инкапсулированные файлы.
     */
    private final Comparator<String> lineComparator;

    public SortedFiles(final Collection<Path> sortedFiles,
                       final Comparator<String> lineComparator) {
        this.sortedFiles = sortedFiles;
        this.lineComparator = lineComparator;
    }

    /**
     * Слияние всех файлов в один результирующий: dest. Слияние происходит
     * последовательно по два файла, пока не будет получен один конечный.
     * Строки в конечный файл будут записаны в отсортированном порядке.
     *
     * Исходные файлы не будут каким либо образом изменены или удалены.
     * Все временные файлы созданные в процессе выполнения будут удалены.
     *
     * @param tempDirectory директория для хранения временных файлов.
     * @param dest конечный файл.
     * @throws IOException если выброшено исключение при чтении исходных файлов,
     * создания временных файлов или записи в конечный файл.
     */
    public final void twoWayMerge(final Path tempDirectory, final Path dest) throws IOException {
        if (this.sortedFiles.isEmpty()) {
            throw new IllegalStateException("No one sorted files.");
        }
        if (this.sortedFiles.size() == 1) {
            Files.copy(this.sortedFiles.iterator().next(), dest);
        } else {
            final Collection<Path> tempFiles = new ArrayList<>();
            try {
                Set<Path> unMerged = new HashSet<>(this.sortedFiles);
                Set<Path> merged = new HashSet<>();
                while (unMerged.size() != 2) {
                    final Iterator<Path> iterator = unMerged.iterator();
                    while (iterator.hasNext()) {
                        final Path firstFile = iterator.next();
                        if (iterator.hasNext()) {
                            final Path tempFile = Files.createTempFile(
                                    tempDirectory, "sorted", ".txt"
                            );
                            tempFiles.add(tempFile);
                            new TwoSortedFiles(
                                    firstFile, iterator.next(), this.lineComparator
                            ).merge(tempFile);
                            merged.add(tempFile);
                        } else {
                            merged.add(firstFile);
                        }
                    }
                    unMerged = merged;
                    merged = new HashSet<>();
                }
                Files.createFile(dest);
                final Iterator<Path> iterator = unMerged.iterator();
                new TwoSortedFiles(
                        iterator.next(), iterator.next(), this.lineComparator
                ).merge(dest);
            } finally {
                tempFiles.forEach(tempFile -> tempFile.toFile().delete());
            }
        }
    }

    /**
     * Слияние всех файлов в один результирующий: dest.
     * Многопутевое слияние в котором производится чтение сразу всех файлов
     * построчно и запись строк в конечный файл с учетом сортировки.
     *
     * !!! Может быть затратно по RAM в случае с большим количеством файлов.
     *
     * Исходные файлы не будут каким либо образом изменены или удалены.
     *
     * @param dest конечный файл.
     * @throws IOException если выброшено исключение при чтении исходных файлов
     * или записи в конечный файл.
     */
    public final void multiWayMerge(final Path dest) throws IOException {
        if (this.sortedFiles.isEmpty()) {
            throw new IllegalStateException("No one sorted files.");
        }
        final Set<CachedReader> readers = new HashSet<>();
        try (final BufferedWriter writer = Files.newBufferedWriter(
                dest, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            this.sortedFiles.forEach(filePath -> {
                try {
                    readers.add(new CachedReader(Files.newBufferedReader(filePath)));
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            });
            do {
                final Optional<CachedReader> readerWithSmallerLine = readers
                        .stream()
                        .reduce((cachedReader, cachedReader2) -> {
                            try {
                                CachedReader result = cachedReader2;
                                if (cachedReader.cached().isEmpty()) {
                                    cachedReader.readLine();
                                }
                                if (cachedReader2.cached().isEmpty()) {
                                    cachedReader2.readLine();
                                }
                                if (cachedReader.cached().isPresent() && cachedReader2.cached().isPresent()) {
                                    final String leftLine = cachedReader.cached().get();
                                    final String rightLine = cachedReader2.cached().get();
                                    if (this.lineComparator.compare(leftLine, rightLine) <= 0) {
                                        result = cachedReader;
                                    }
                                }
                                return result;
                            } catch (IOException ex) {
                                throw new UncheckedIOException(ex);
                            }
                        });
                if (readerWithSmallerLine.isPresent()) {
                    final CachedReader reader = readerWithSmallerLine.get();
                    final Optional<String> optLine = reader.cached();
                    if (optLine.isPresent()) {
                        writer.write(optLine.get());
                        writer.newLine();
                        reader.readLine();
                    }
                }
                readers.removeIf(cachedReader -> cachedReader.cached().isEmpty());
            } while (!readers.isEmpty());
        } finally {
            for (CachedReader reader : readers) {
                reader.close();
            }
        }
    }

    /**
     * CachedReader.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 18.01.2019
     */
    public final static class CachedReader implements AutoCloseable {
        private final BufferedReader reader;
        private String previousLine;

        public CachedReader(final BufferedReader reader) {
            this.reader = reader;
        }

        /**
         * Читает следующую строку из reader и записывает в cache,
         * доступ к которому можно получить через метод: cached().
         * @throws IOException если выброшено исключение
         * при чтении следующей строки.
         * @return false, если reader достиг конца потока данных.
         */
        public final boolean readLine() throws IOException {
            this.previousLine = this.reader.readLine();
            return Objects.nonNull(this.previousLine);
        }

        /**
         * Возвращает строку прочитанную ранее методом readLine().
         * Если метод readLine() ни разу не вызывался, то вернет Optional.empty.
         * Optional.empty может означать, что reader пуст.
         * @return Optional.of прошлая прочитанная строка.
         */
        public final Optional<String> cached() {
            return Optional.ofNullable(this.previousLine);
        }

        @Override
        public final void close() throws IOException {
            this.reader.close();
        }
    }
}
