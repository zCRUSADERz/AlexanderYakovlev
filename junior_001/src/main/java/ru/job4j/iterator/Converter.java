package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Converter of iterators.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 28.01.2017
 * @version 1.0
 */
public class Converter {

    /**
     * Convert Iterator of Iterator<Integer>.
     * @param it - iterator.
     * @return - iterator of numbers.
     */
    Iterator<Integer> convert(final Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> nextIterator = it.next();

            /**
             * Returns true if the iteration has more numbers.
             * @return - true, if has next number.
             */
            @Override
            public boolean hasNext() {
                boolean result = false;
                while (true) {
                    if (!nextIterator.hasNext()) {
                        if (it.hasNext()) {
                            nextIterator = it.next();
                        } else {
                            break;
                        }
                    } else {
                        result = true;
                        break;
                    }
                }
                return result;
            }

            /**
             * Returns the next number.
             * @return - next number.
             */
            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Iteration has no more elements");
                }
                return nextIterator.next();
            }

            /**
             * Unsupported operation.
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
