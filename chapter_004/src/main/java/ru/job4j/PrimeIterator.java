package ru.job4j;

import java.util.*;

/**
 * Prime numbers iterator.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 28.01.2017
 * @version 1.0
 */
public class PrimeIterator implements Iterator {
    private int[] numbers;
    private Set<Integer> primeNumbers = new HashSet<>();
    private int cursor;
    private int size;

    /**
     * Default constructor.
     * @param numbers - array of numbers.
     */
    PrimeIterator(int[] numbers) {
        this.numbers = numbers;
        size = numbers.length;
        cursor = -1;
        setPrimeNumbers();
        setCursor();
    }

    /**
     * Returns true if the iteration has more prime numbers.
     * @return - true, if has next prime number.
     */
    @Override
    public boolean hasNext() {
        return cursor != size;
    }

    /**
     * Returns the next prime number.
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
     * Finding for all the primes we need.
     */
    private void setPrimeNumbers() {
        int max = 0;
        for (int n : numbers) {
            if (max < n) {
                max = n;
            }
        }
        if (max > 1) {
            boolean[] s = new boolean[max + 1];
            Arrays.fill(s, true);
            for (int k = 2; k <= max; k++) {
                if (s[k]) {
                    for (int l = k * k; l <= max; l += k) {
                        s[l] = false;
                    }
                }
            }
            for (int i = 2; i <= max; i++) {
                if (s[i]) {
                    primeNumbers.add(i);
                }
            }
        }

    }

    /**
     * Sets the cursor on the next prime number.
     */
    private void setCursor() {
        while (++cursor < size && !primeNumbers.isEmpty()) {
            if ((primeNumbers.contains(numbers[cursor]))) {
                break;
            }
        }
    }
}
