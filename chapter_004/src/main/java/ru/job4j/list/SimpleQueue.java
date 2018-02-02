package ru.job4j.list;

/**
 * Simple queue.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.02.2017
 */
public class SimpleQueue<E> {
    private LinkedList<E> list;
    private int size;

    SimpleQueue() {
        list = new LinkedList<>();
        size = 0;
    }

    /**
     * Pushes an element onto the queue.
     * @param value - element to push.
     */
    public void push(E value) {
        list.add(value);
        size++;
    }

    /**
     * Retrieves and removes the first element of this queue.
     * @return - element, or null if this queue is empty
     */
    public E poll() {
        E result = null;
        if (size != 0) {
            result = list.remove(0);
            size--;
        }
        return result;
    }
}
