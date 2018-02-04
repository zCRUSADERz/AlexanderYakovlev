package ru.job4j.set;

import ru.job4j.list.ArrayList;

import java.util.Iterator;

/**
 * Simple set.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.02.2017
 */
public class SimpleSet<E> implements Iterable<E> {
    private ArrayList<E> list;

    public SimpleSet() {
        list = new ArrayList<>();
    }

    /**
     * Add element to this set if it is not already present.
     * @param value - element to be added to this set.
     */
    public void add(E value) {
        if (!contains(value)) {
            list.add(value);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    /**
     * Returns true if this set contains the element.
     * @param value -  element whose presence in this set is to be tested.
     * @return - true if this set contains the element
     */
    public boolean contains(E value) {
        boolean exist = false;
        if (value == null) {
            for (E element : list) {
                if (element == null) {
                    exist = true;
                    break;
                }
            }
        } else {
            for (E element : list) {
                if (value.equals(element)) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;
    }
}
