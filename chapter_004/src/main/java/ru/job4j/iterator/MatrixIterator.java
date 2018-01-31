package ru.job4j.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Matrix array iterator.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 28.01.2017
 * @version 1.0
 */
public class MatrixIterator<T> implements Iterator<T> {
    private T[][] array;
    /**
     * Index of next element returns.
     */
    private int i = 0;
    /**
     * Index of next element returns in subarray;
     */
    private int j = 0;
    /**
     * Index of last element returned.
     */
    private int previousI = 0;
    /**
     * Index of last element returned in subarray; -1 if no such.
     */
    private int previousJ = -1;

    /**
     * Default constructor.
     * @param array - source array.
     */
    MatrixIterator(T[][] array) {
        this.array = array;
    }

    /**
     * Returns true if the iteration has more elements.
     * @return - true, if has next element.
     */
    @Override
    public boolean hasNext() {
        boolean exist = false;
        if (array.length != 0) {
            if (j >= array[i].length) {
                for (int next = i + 1; next < array.length; next++) {
                    if (array[next].length > 0) {
                        i = next;
                        j = 0;
                        exist = true;
                        break;
                    }
                }
            } else {
                exist = true;
            }
        }
        return exist;
    }

    /**
     * Returns the next element.
     * @return - next element.
     */
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iteration has no more elements");
        }
        T result = array[i][j];
        previousI = i;
        previousJ = j++;
        return result;
    }

    /**
     * Removes the last element returned by this iterator.
     */
    @Override
    public void remove() {
        if (previousJ == -1) {
            throw new IllegalStateException();
        }
        int size = array[previousI].length;
        if (size == 1) {
            array[previousI] = Arrays.copyOf(array[previousI], 0);
        } else {
            System.arraycopy(
                    array[previousI], previousJ + 1,
                    array[previousI], previousJ,
                    size - previousJ - 1
            );
            if (previousJ < size - 1) {
                j--;
            }
            array[previousI] = Arrays.copyOf(array[previousI], size - 1);
        }
        previousJ = -1;
    }
}
