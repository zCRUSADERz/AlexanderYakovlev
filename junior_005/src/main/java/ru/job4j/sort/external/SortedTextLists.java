package ru.job4j.sort.external;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

/**
 * SortedTextLists.
 * Объект представляет коллекцию отсортированных списков с текстом.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 18.01.2019
 */
public final class SortedTextLists {
    private final Iterable<List<String>> lists;

    /**
     * Дополнительный конструктор.
     * Конструирует данный объект используя TextFile.splitByLines()
     * и SortedListsIterator, в результате чего получаем набор отсортированных
     * списков с текстом.
     * @param textFile текстовый файл.
     * @param partSize размер в байтах по которому будет разделен текстовый файл.
     * @param lineComparator comparator для сортировки строк.
     */
    public SortedTextLists(final TextFile textFile, final long partSize,
                           final Comparator<String> lineComparator) {
        this(() -> new SortedListsIterator(
                textFile.splitByLines(partSize),
                lineComparator
                )
        );
    }

    /**
     * Основной конструктор.
     * @param lists набор списков.
     */
    public SortedTextLists(final Iterable<List<String>> lists) {
        this.lists = lists;
    }

    /**
     * Записать каждый список в отдельный файл.
     * @param filePathGenerator Path генератор для каждого списка.
     *                          Список будет записан в файл по полученному Path.
     * @throws IOException в случае если было выброшено исключение
     * во время записи файла.
     */
    public final void writeSeparately(final Function<List<String>, Path> filePathGenerator) throws IOException {
        for (final List<String> list : this.lists) {
            Files.write(filePathGenerator.apply(list), list);
        }
    }

    /**
     * SortedListsIterator.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 18.01.2019
     */
    public final static class SortedListsIterator implements Iterator<List<String>> {
        /**
         * Итератор списков с неотсортированными строками.
         */
        private final Iterator<List<String>> origin;
        /**
         * Comparator для сортировки строк.
         */
        private final Comparator<String> comparator;

        private SortedListsIterator(final Iterator<List<String>> origin,
                                    final Comparator<String> comparator) {
            this.origin = origin;
            this.comparator = comparator;
        }

        @Override
        public final boolean hasNext() {
            return origin.hasNext();
        }

        /**
         * Возвращает следующий отсортированный список строк.
         * @return следующий отсортированный список строк.
         */
        @Override
        public final List<String> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final List<String> lines = this.origin.next();
            lines.sort(this.comparator);
            return lines;
        }
    }
}
