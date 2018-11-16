package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple Hash Map.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.03.2018
 */
public class SimpleHashMap<K, V> implements Iterable<SimpleHashMap.Node<K, V>> {
    private Node<K, V>[] objects;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75f;
    private int size = 0;
    private int threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    /**
     * Structurally modification count.
     */
    private int modCount = 0;

    public SimpleHashMap() {
        objects = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
    }

    /**
     * Insert key with value to this map if it is not already present.
     * @param key - key.
     * @param value - value.
     * @return - true, if key with value to be added.
     */
    public boolean insert(K key, V value) {
        boolean result = false;
        if (size > threshold) {
            resize();
        }
        int index = indexFor(key.hashCode());
        if (objects[index] == null || !key.equals(objects[index].key)) {
            add(index, key, value);
            result = true;
        }
        return result;
    }

    /**
     * Returns the value to which key is mapped..
     * @param key - key.
     * @return - value.
     */
    public V get(K key) {
        V result = null;
        Node<K, V> node = objects[indexFor(key.hashCode())];
        if (node != null) {
            result = node.value;
        }
        return result;
    }

    /**
     * Delete the mapping for the key from this map is present.
     * @param key - key.
     * @return - true, if deleted.
     */
    public boolean delete(K key) {
        boolean result = false;
        int index = indexFor(key.hashCode());
        if (objects[index] != null && key.equals(objects[index].key)) {
            objects[index] = null;
            size--;
            modCount++;
            result = true;
        }
        return result;
    }

    private void resize() {
        Node<K, V>[] old = objects;
        int newCapacity = objects.length * 2;
        objects = (Node<K, V>[]) new Node[newCapacity];
        threshold = (int) (newCapacity * LOAD_FACTOR);
        size = 0;
        for (Node<K, V> node : old) {
            if (node != null) {
                addNode(node);
            }
        }
    }

    private void add(int index, K key, V value) {
        objects[index] = new Node<>(key, value);
        size++;
        modCount++;
    }

    private void addNode(Node<K, V> node) {
        objects[indexFor(node.key.hashCode())] = node;
        size++;
        modCount++;
    }

    private int indexFor(int hash) {
        return hash & (objects.length - 1);
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iterator<Node<K, V>>() {
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
            public Node<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<K, V> result;
                do {
                    result = objects[nextIndex++];
                } while (result == null);
                lastReturned = nextIndex - 1;
                count++;
                return result;
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
                SimpleHashMap.this.delete(objects[lastReturned].key);
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

    public static final class Node<K, V> {
        private final K key;
        private final V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K key() {
            return key;
        }

        public V value() {
            return value;
        }
    }
}
