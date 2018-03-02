package ru.job4j.set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple Hash Set.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 01.03.2018
 */
public class SimpleHashSet<E> implements Iterable<E> {
    private Object[] objects;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.5f;
    private int size = 0;
    private int threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    /**
     * Structurally modification count.
     */
    private int modCount = 0;

    public SimpleHashSet() {
        objects = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Add element to this set if it is not already present.
     * @param value - element to be added to this set.
     */
    public boolean add(E value) {
        boolean result = false;
        if (size > threshold) {
            resize();
        }
        if (!contains(value)) {
            addValue(value);
            result = true;
        }
        return result;
    }

    /**
     * Returns true if this set contains the element.
     * @param value -  element whose presence in this set is to be tested.
     * @return - true if this set contains the element
     */
    public boolean contains(E value) {
        return value.equals(objects[indexFor(value.hashCode())]);
    }

    public boolean remove(E value) {
        boolean result = false;
        int index = indexFor(value.hashCode());
        if (value.equals(objects[index])) {
            objects[index] = null;
            size--;
            modCount++;
            result = true;
        }
        return result;
    }

    private void resize() {
        Object[] old = objects;
        int newCapacity = objects.length * 2;
        objects = new Object[newCapacity];
        threshold = (int) (newCapacity * LOAD_FACTOR);
        size = 0;
        for (Object obj : old) {
            if (obj != null) {
                addValue((E) obj);
            }
        }
    }

    private void addValue(E value) {
        objects[indexFor(value.hashCode())] = value;
        size++;
        modCount++;
    }

    private int indexFor(int hash) {
        return hash & (objects.length - 1);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int count = 0;
            private int nextIndex = 0;
            private int lastReturned = -1;
            private int expectedModCount = modCount;

            /**
             * Returns true if the iteration has more element.
             * @return - true, if has next element.
             */
            @Override
            public boolean hasNext() {
                checkModification();
                return count < size;
            }

            /**
             * Returns the next element.
             * @return - next element.
             */
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Object result;
                do {
                    result = objects[nextIndex++];
                } while (result == null);
                lastReturned = nextIndex - 1;
                count++;
                return (E) result;
            }

            /**
             * Removes the last element returned by this iterator.
             */
            @Override
            public void remove() {
                checkModification();
                if (lastReturned == -1) {
                    throw new IllegalStateException();
                }
                SimpleHashSet.this.remove((E) objects[lastReturned]);
                lastReturned = -1;
                expectedModCount++;
                count--;
            }

            /**
             * If the list has been changed, throws ConcurrentModificationException.
             */
            private void checkModification() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}
