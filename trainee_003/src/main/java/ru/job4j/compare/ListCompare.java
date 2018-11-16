package ru.job4j.compare;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * User.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 15.01.2017
 * @version 1.0
 */
public class ListCompare implements Comparator<List<Integer>> {

    /**
     * Compare two list.
     * @param left - first list.
     * @param right - second list.
     * @return - -1 if the first size is less or value of elements is less.
     */
    @Override
    public int compare(List<Integer> left, List<Integer> right) {
        int result = Integer.compare(left.size(), right.size());
        if (result == 0) {
            Iterator<Integer> firstIterator = left.iterator();
            Iterator<Integer> secondIterator = right.iterator();
            while (firstIterator.hasNext()) {
                int tmp = Integer.compare(firstIterator.next(), secondIterator.next());
                if (tmp != 0) {
                    result = tmp;
                    break;
                }
            }
        }
        return result;
    }
}
