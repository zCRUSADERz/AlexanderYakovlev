package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Linked list.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.02.2017
 */
@ThreadSafe
public class LinkedList<E> implements Iterable<E> {
    @GuardedBy("this")
    private volatile Node<E> first;
    @GuardedBy("this")
    private volatile Node<E> last;
    @GuardedBy("this")
    private volatile int size;
    /**
     * Structurally modification count.
     */
    @GuardedBy("this")
    private volatile int modCount;

    public LinkedList() {
        first = null;
        last = null;
        size = 0;
        modCount = 0;
    }

    /**
     * Add element to the end.
     * @param value - element.
     */
    public synchronized void add(E value) {
        Node<E> previous = last;
        Node<E> newNode = new Node<>(previous, value, null);
        last = newNode;
        if (first == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * Returns the element at the index position.
     * @param index - position.
     * @return - element at the index position.
     */
    public synchronized E get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    public synchronized E remove(int index) {
        checkIndex(index);
        return removeNode(node(index));
    }

    private Node<E> node(int index) {
        Node<E> result;
        if (index < (size >> 1)) {
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            result = node;
        } else {
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
            result = node;
        }
        return result;
    }

    /**
     * Remove node and restores links between nodes.
     * @param node - removable node.
     */
    private synchronized E removeNode(Node<E> node) {
        Node<E> next = node.next;
        Node<E> previous = node.previous;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }
        if (next == null) {
            last = previous;
        } else {
            next.previous = previous;
        }

        E result = node.item;
        node.item = null;
        node.previous = null;
        node.next = null;

        size--;
        modCount++;
        return result;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> previous;

        Node(Node<E> previous, E item, Node<E> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }

    private class Itr implements Iterator<E> {
        private Node<E> next;
        private Node<E> lastReturned;
        private int cursor;
        private int expectedModCount;

        Itr() {
            next = first;
            lastReturned = null;
            cursor = 0;
            expectedModCount = modCount;
        }

        /**
         * Returns true if the iteration has more element.
         * @return - true, if has next element.
         */
        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        /**
         * Returns the next element.
         * @return - next element.
         */
        @Override
        public E next() {
            checkModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = next;
            next = next.next;
            cursor++;
            return lastReturned.item;
        }

        /**
         * Removes the last element returned by this iterator.
         */
        @Override
        public void remove() {
            checkModification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            removeNode(lastReturned);
            cursor--;
            lastReturned = null;
            expectedModCount++;
        }

        /**
         * If the list has been changed, throws ConcurrentModificationException.
         */
        private void checkModification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
