package ru.job4j;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Even numbers iterator.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 28.01.2017
 * @version 1.0
 */
public class EvenNumbersIterator implements Iterator {
    private int[] numbers;
    private int cursor;
    private int size;

    /**
     * Default constructor.
     * @param numbers - array of numbers.
     */
    EvenNumbersIterator(int[] numbers) {
        this.numbers = numbers;
        size = numbers.length;
        cursor = -1;
        setCursor();
    }

    /**
     * Returns true if the iteration has more even numbers.
     * @return - true, if has next element.
     */
    @Override
    public boolean hasNext() {
        return cursor != size;
    }

    /**
     * Returns the next even number.
     * @return - next number.
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iteration has no more elements");
        }
        int result = numbers[cursor];
        setCursor();
        return result;
    }

    /**
     * Unsupported operation.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the cursor on the next even number.
     */
    private void setCursor() {
        while (++cursor < size) {
            if ((numbers[cursor] % 2) == 0) {
                break;
            }
        }
    }
}
