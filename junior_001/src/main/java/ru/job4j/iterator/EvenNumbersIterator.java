package ru.job4j.iterator;

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
        cursor = 0;
    }

    /**
     * Returns true if the iteration has more even numbers.
     * @return - true, if has next element.
     */
    @Override
    public boolean hasNext() {
        boolean exist = false;
        for (int i = cursor; i < size; i++) {
            if (isEven(numbers[i])) {
                cursor = i;
                exist = true;
                break;
            }
        }
        return exist;
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
        return numbers[cursor++];

    }

    /**
     * Unsupported operation.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks an even number.
     * @param num - number.
     * @return if num even then true.
     */
    private boolean isEven(int num) {
        return (num % 2) == 0;
    }
}
