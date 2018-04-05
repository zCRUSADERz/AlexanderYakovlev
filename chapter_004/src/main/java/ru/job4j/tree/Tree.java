package ru.job4j.tree;

import java.util.*;

public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    private final Node<E> root;
    private int modCount = 0;

    public Tree(E value) {
        this.root = new Node<>(value);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        if (!findBy(child).isPresent()) {
            Optional<Node<E>> opt = findBy(parent);
            if (opt.isPresent()) {
                Node<E> parentNode = opt.get();
                parentNode.add(new Node<>(child));
                modCount++;
                result = true;
            }
        }
        return result;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    /**
     * Проверяет двоичное ли дерево.
     * @return - true, если двоичное.
     */
    @Override
    public boolean isBinary() {
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(root);
        int counter = 0;
        while (counter <= 2 && !data.isEmpty()) {
            counter = 0;
            Node<E> node = data.poll();
            for (Node<E> childs : node.leaves()) {
                data.offer(childs);
                counter++;
            }
        }
        return counter <= 2;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        private final Queue<Node<E>> data;
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
            Node<E> node = data.poll();
            result = node.value();
            for (Node<E> childs : node.leaves()) {
                data.offer(childs);
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
