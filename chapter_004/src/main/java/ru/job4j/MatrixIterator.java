package ru.job4j;

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
    private int firstIndex = 0;
    /**
     * Index of next element returns in subarray; -1 if no such.
     */
    private int secondIndex = -1;
    /**
     * Index of last element returned.
     */
    private int previousFirstIndex = 0;
    /**
     * Index of last element returned in subarray; -1 if no such.
     */
    private int previousSecondIndex = -1;

    /**
     * Default constructor.
     * @param array - source array.
     */
    MatrixIterator(T[][] array) {
        this.array = array;
        setNextIndex();
    }

    /**
     * Returns true if the iteration has more elements.
     * @return - true, if has next element.
     */
    public boolean hasNext() {
        return secondIndex != -1;
    }

    /**
     * Returns the next element
     * @return - next element.
     */
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iteration has no more elements");
        }
        T result = array[firstIndex][secondIndex];
        previousFirstIndex = firstIndex;
        previousSecondIndex = secondIndex;
        setNextIndex();
        return result;
    }

    /**
     * Removes the last element returned by this iterator.
     */
    public void remove() {
        if (previousSecondIndex == -1) {
            throw new IllegalStateException();
        }
        int size = array[previousFirstIndex].length;
        if (size == 1) {
            array[previousFirstIndex] = Arrays.copyOf(array[previousFirstIndex], 0);
        } else {
            System.arraycopy(
                    array[previousFirstIndex], previousSecondIndex + 1,
                    array[previousFirstIndex], previousSecondIndex,
                    size - previousSecondIndex - 1
            );
            if (previousSecondIndex < size - 1) {
                secondIndex--;
            }
            array[previousFirstIndex] = Arrays.copyOf(array[previousFirstIndex], size - 1);
        }
        previousSecondIndex = -1;
    }

    /**
     * Set first and second Index of the next element.
     */
    private void setNextIndex() {
        if (array.length != 0) {
            secondIndex++;
            boolean exist = false;
            if (secondIndex >= array[firstIndex].length) {
                firstIndex++;
                for (int i = firstIndex; i < array.length; i++) {
                    if (array[i].length > 0) {
                        firstIndex = i;
                        secondIndex = 0;
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    secondIndex = -1;
                }
            }
        }
    }
}
