package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple Array.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 28.01.2017
 * @version 1.0
 */
public class SimpleArray<T> implements Iterable<T> {
    private Object[] array;
    private int position;

    /**
     * Default constructor.
     * @param capacity - capacity array.
     * @throws IllegalArgumentException - if capacity is negative.
     */
    SimpleArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        }
        array = new Object[capacity];
        position = capacity != 0 ? 0 : -1;
    }

    /**
     * Add element.
     * @param element - added element.
     */
    public void add(T element) {
        array[position++] = element;
    }

    /**
     * Set new element at the index position.
     * @param index - index of the element to replace.
     * @param element - element.
     */
    public void set(int index, T element) {
        rangeCheck(index);
        array[index] = element;
    }

    /**
     * Delete element.
     * @param index - index of the element to delete.
     */
    public void delete(int index) {
        rangeCheck(index);
        System.arraycopy(array, index + 1, array, index, position - index);
        position--;
    }

    /**
     * Return element.
     * @param index - index of the element to return.
     * @return - element.
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        rangeCheck(index);
        return (T) array[index];
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;
            private int previous = -1;

            /**
             * Returns true if the iteration has more elements.
             * @return - true, if has next element.
             */
            @Override
            public boolean hasNext() {
                return cursor < position;
            }

            /**
             * Returns the next element.
             * @return - next element.
             * @throws NoSuchElementException - if the iteration has no more elements.
             */
            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Object result = array[cursor];
                previous = cursor++;
                return (T) result;
            }

            /**
             * Removes the last element returned by this iterator.
             * @throws IllegalStateException - if the next method has not yet been called,
             * or the remove method has already been called after the last call to the next method
             */
            @Override
            public void remove() {
                if (previous == -1) {
                    throw new IllegalStateException();
                }
                delete(previous);
                cursor = previous;
                previous = -1;
            }
        };
    }

    /**
     * Check range position.
     * @param index - index of element.
     */
    private void rangeCheck(int index) {
        if (index >= position) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + position);
        }
    }
}
