package ru.job4j.sort.external;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

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
                final Queue<Path> queue = new LinkedList<>(this.sortedFiles);
                while (queue.size() > 2) {
                    final Path first = queue.poll();
                    final Path second = queue.poll();
                    final Path tempFile = Files.createTempFile(
                            tempDirectory, "sorted", ".txt"
                    );
                    tempFiles.add(tempFile);
                    new TwoSortedFiles(first, second, this.lineComparator)
                            .mergeToFile(tempFile);
                    queue.offer(tempFile);
                }
                Files.createFile(dest);
                new TwoSortedFiles(
                        queue.poll(), queue.poll(), this.lineComparator
                ).mergeToFile(dest);
            } finally {
                for (Path tempFile : tempFiles) {
                    tempFile.toFile().delete();
                }
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
        Optional<IOException> optException = Optional.empty();
        final List<Stream<String>> filesStream
                = new ArrayList<>(this.sortedFiles.size());
        try {
            for (Path filePath : this.sortedFiles) {
                filesStream.add(Files.lines(filePath, UTF_8));
            }
            final Queue<Iterable<String>> queue = filesStream
                    .stream()
                    .map(stream -> (Iterable<String>) stream::iterator)
                    .collect(Collectors.toCollection(LinkedList::new));
            while (queue.size() != 1) {
                final Iterable<String> left = queue.poll();
                final Iterable<String> right = queue.poll();
                queue.offer(new TwoSortedIterables<>(
                        left, right, this.lineComparator
                ));
            }
            Files.write(
                    dest, queue.poll(),
                    StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE
            );
        } catch (UncheckedIOException ex) {
            optException = Optional.of(ex.getCause());
        } catch (IOException ex) {
            optException = Optional.of(ex);
        }
        final Collection<Exception> exceptions = new ArrayList<>();
        optException.ifPresent(exceptions::add);
        for (Stream<String> stream : filesStream) {
            try {
                stream.close();
            } catch (Exception ex) {
                exceptions.add(ex);
            }
        }
        if (!exceptions.isEmpty()) {
            final IOException ex = new IOException();
            for (Exception exception : exceptions) {
                ex.addSuppressed(exception);
            }
            throw ex;
        }
    }
}
