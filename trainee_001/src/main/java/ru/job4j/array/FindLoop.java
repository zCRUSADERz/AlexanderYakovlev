package ru.job4j.array;

/**
 * FindLoop.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class FindLoop {

    /**
     * Search index of element in array;
     * @param data - data array.
     * @param el - search element.
     * @return - index of search element.
     */
    public int indexOf(int[] data, int el) {
        int result = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == el) {
                result = i;
                break;
            }
        }
        return result;
    }
}
