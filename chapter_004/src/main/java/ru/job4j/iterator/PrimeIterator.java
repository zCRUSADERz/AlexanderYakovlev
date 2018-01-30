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
    private List<Integer> primeNumbers = new ArrayList<>();
    private Iterator<Integer> it;

    /**
     * Default constructor.
     * @param numbers - array of numbers.
     */
    PrimeIterator(int[] numbers) {
        this.numbers = numbers;
        initPrimeNumbers();
        it = primeNumbers.iterator();
    }

    /**
     * Returns true if the iteration has more prime numbers.
     * @return - true, if has next prime number.
     */
    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    /**
     * Returns the next prime number.
     * @return - next number.
     */
    @Override
    public Object next() {
        return it.next();
    }

    /**
     * Unsupported operation.
     */
    @Override
    public void remove() {
        it.remove();
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
            boolean[] s = new boolean[max + 1];
            Arrays.fill(s, 2, max + 1, true);
            for (int k = 2; k <= max; k++) {
                if (s[k]) {
                    for (int l = k * k; l <= max; l += k) {
                        s[l] = false;
                    }
                }
            }
            for (int n : numbers) {
                if (s[n]) {
                    primeNumbers.add(n);
                }
            }
        }

    }
}
