package ru.job4j.list;

/**
 * Simple stack.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.02.2017
 */
public class SimpleStack<E> {
    private LinkedList<E> list;
    private int size;

    SimpleStack() {
        list = new LinkedList<>();
        size = 0;
    }

    /**
     * Pushes an item onto the top of this stack.
     * @param value - element to push.
     */
    public void push(E value) {
        list.add(value);
        size++;
    }

    /**
     * Removes the element at the top of this stack and returns that element.
     * @return - element at the top of this stack.
     */
    public E poll() {
        E result = null;
        if (size != 0) {
            result = list.remove(size - 1);
            size--;
        }
        return result;
    }
}
