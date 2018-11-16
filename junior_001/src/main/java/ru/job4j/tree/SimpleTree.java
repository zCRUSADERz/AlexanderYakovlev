package ru.job4j.tree;

import java.util.Optional;

public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {

    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     * @param parent parent.
     * @param child child.
     * @return true, if added
     */
    boolean add(E parent, E child);

    /**
     * Ищет элемент в дереве.
     * @param value - значение.
     * @return - возвращает ноду, с этим элементов.
     */
    Optional<Node<E>> findBy(E value);

    /**
     * Проверяет двоичное ли дерево.
     * @return - true, если двоичное.
     */
    boolean isBinary();
}
