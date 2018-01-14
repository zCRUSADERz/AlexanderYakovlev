package ru.job4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Convert List to array and from array.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.01.2017
 * @version 1.0
 */
public class ConvertList {

    /**
     * Convert array to List.
     * @param array - array.
     * @return - List.
     */
    public List<Integer> toList(int[][] array) {
        int capacity = 0;
        for (int[] arr : array) {
            capacity += arr.length;
        }
        List<Integer> list = new ArrayList<>((int) (capacity * 1.25));
        for (int[] arr : array) {
            for (int num : arr) {
                list.add(num);
            }
        }
        return list;
    }

    /**
     * Convert List to array.
     * @param list - List.
     * @param rows - rows in array.
     * @return - array.
     */
    public int[][] toArray(List<Integer> list, int rows) {
        double size = (double) list.size() / rows;
        int column = (size % (int) size) != 0 ? (int) size + 1 : (int) size;
        int[][] array = new int[rows][column];
        Iterator<Integer> iterator = list.iterator();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                array[i][j] = iterator.hasNext() ? iterator.next() : 0;
            }
        }
        return array;
    }

    /**
     * Convert List arrays to List.
     * @param list - List with arrays.
     * @return - List.
     */
    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] array : list) {
            for (int num : array) {
                result.add(num);
            }
        }
        return result;
    }
}