package ru.job4j.tree;

import java.util.*;

public class BinarySearchTree<E extends Comparable<E>> implements Iterable<E> {
    private final BinaryNode<E> root;
    private int modCount;

    public BinarySearchTree(E value) {
        this.root = new BinaryNode<>(value);
        this.modCount = 0;
    }

    public void add(E value) {
        root.add(value);
        modCount++;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        private final Queue<BinaryNode<E>> data;
        private int expectedModCount;

        private Itr() {
            this.data = new LinkedList<>();
            this.data.offer(root);
            this.expectedModCount = modCount;
        }

        /**
         * Returns true if the iteration has more element.
         * @return - true, if has next element.
         */
        @Override
        public boolean hasNext() {
            checkModification();
            return !data.isEmpty();
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
            E result;
            BinaryNode<E> node = data.poll();
            result = node.value();
            if (node.left() != null) {
                data.offer(node.left());
            }
            if (node.right() != null) {
                data.offer(node.right());
            }
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
         * If the list has been changed, throws ConcurrentModificationException.
         */
        private void checkModification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
