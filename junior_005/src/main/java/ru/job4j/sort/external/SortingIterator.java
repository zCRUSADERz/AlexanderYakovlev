package ru.job4j.sort.external;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Sorting iterator.
 * Итератор инкапсулирует два других итератора, которые итерируют
 * отсортированную последовательность элементов.
 * Производит слияние двух отсортированных последовательностей в одну
 * с сохранением отсортированности элементов.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 18.01.2019
 */
public final class SortingIterator<T> implements Iterator<T> {
    private final Iterator<T> left;
    private final Iterator<T> right;
    private final Comparator<T> comparator;
    /**
     * Предыдущее значение, возвращенное this.left итератором.
     */
    private T leftValue;
    /**
     * Предыдущее значение, возвращенное this.right итератором.
     */
    private T rightValue;

    public SortingIterator(final Iterator<T> sortedLeft, final Iterator<T> sortedRight,
                           final Comparator<T> comparator) {
        this.left = sortedLeft;
        this.right = sortedRight;
        this.comparator = comparator;
    }

    @Override
    public final boolean hasNext() {
        if (Objects.isNull(this.leftValue) && left.hasNext()) {
            this.leftValue = this.left.next();
        }
        if (Objects.isNull(this.rightValue) && right.hasNext()) {
            this.rightValue = this.right.next();
        }
        return Objects.nonNull(this.leftValue)
                || Objects.nonNull(this.rightValue);
    }

    @Override
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        final T result;
        boolean leftLessOrEqualThenRight = Objects.nonNull(this.leftValue)
                && Objects.nonNull(this.rightValue)
                && (this.comparator.compare(
                this.leftValue, this.rightValue) <= 0);
        if (leftLessOrEqualThenRight || Objects.isNull(this.rightValue)) {
            result = this.leftValue;
            this.leftValue = null;
        } else {
            result = this.rightValue;
            this.rightValue = null;
        }
        return result;
    }
}
