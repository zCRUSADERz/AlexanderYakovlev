package ru.job4j.sort.external;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Two sorted iterables.
 * Два отсортированных набора элементов.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.01.2019
 */
public final class TwoSortedIterables<T> implements Iterable<T> {
    private final Iterable<T> left;
    private final Iterable<T> right;
    private final Comparator<T> comparator;

    public TwoSortedIterables(final Iterable<T> left, final Iterable<T> right,
                              final Comparator<T> comparator) {
        this.left = left;
        this.right = right;
        this.comparator = comparator;
    }

    @Override
    public final Iterator<T> iterator() {
        return new SortingIterator<>(
                this.left.iterator(),
                this.right.iterator(),
                this.comparator
        );
    }
}
