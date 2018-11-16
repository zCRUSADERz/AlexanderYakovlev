package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array list.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 31.01.2017
 */
@ThreadSafe
public class ArrayList<E> implements Iterable<E> {
    @GuardedBy("this")
    private volatile Object[] objects;
    private static final int DEFAULT_CAPACITY = 10;
    @GuardedBy("this")
    private volatile int size = 0;

    /**
     * Structurally modification count.
     */
    @GuardedBy("this")
    private volatile int modCount = 0;

    public ArrayList() {
        objects = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructor with initial capacity.
     * @param capacity - initial capacity.
     */
    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity can not be less than 0.");
        }
        capacity = (capacity <= DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
        objects = new Object[capacity];
    }

    /**
     * Add element to the end.
     * @param value - element.
     */
    public synchronized void add(E value) {
        if (size + 1 > objects.length) {
            int newCapacity = size + (size >> 1);
            objects = Arrays.copyOf(objects, newCapacity);
        }
        objects[size++] = value;
        modCount++;
    }

    /**
     * Returns the element at the index position.
     * @param index - position.
     * @return - element at the index position.
     */
    @SuppressWarnings("unchecked")
    public synchronized E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) objects[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int cursor = 0;
            private int expectedModCount = modCount;

            /**
             * Returns true if the iteration has more element.
             * @return - true, if has next element.
             */
            @Override
            public boolean hasNext() {
                checkModification();
                return cursor < size;
            }

            /**
             * Returns the next element.
             * @return - next element.
             */
            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) objects[cursor++];
            }

            /**
             * Unsupported operation.
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
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
