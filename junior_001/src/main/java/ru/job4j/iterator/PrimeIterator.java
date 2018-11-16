package ru.job4j.iterator;

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
    private Set<Integer> primeNumbers;
    private boolean initializePrimeNumbers;
    private int cursor;
    private int size;

    /**
     * Default constructor.
     * @param numbers - array of numbers.
     */
    PrimeIterator(int[] numbers) {
        this.numbers = numbers;
        size = numbers.length;
        cursor = 0;
        primeNumbers = new HashSet<>();
        initializePrimeNumbers = false;
    }

    /**
     * Returns true if the iteration has more prime numbers.
     * @return - true, if has next prime number.
     */
    @Override
    public boolean hasNext() {
        if (!initializePrimeNumbers) {
            initPrimeNumbers();
        }
        boolean exist = false;
        for (int i = cursor; i < size; i++) {
            if (primeNumbers.contains(numbers[i])) {
                cursor = i;
                exist = true;
                break;
            }
        }
        return exist;
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
     * Finding for all the primes we need.
     */
    private void initPrimeNumbers() {
        int max = 0;
        for (int n : numbers) {
            if (max < n) {
                max = n;
            }
        }
        if (max > 1) {
            boolean[] notPrime = new boolean[max + 1];
            notPrime[0] = true;
            notPrime[1] = true;
            for (int k = 2; k <= max; k++) {
                if (!notPrime[k]) {
                    for (int l = k * k; l <= max; l += k) {
                        notPrime[l] = true;
                    }
                }
            }
            for (int n : numbers) {
                if (!notPrime[n]) {
                    primeNumbers.add(n);
                }
            }
        }
        initializePrimeNumbers = true;
    }
}
