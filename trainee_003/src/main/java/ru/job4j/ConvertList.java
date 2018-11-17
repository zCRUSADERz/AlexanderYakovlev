package ru.job4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return Arrays.stream(array)
                .flatMapToInt(Arrays::stream)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Convert List to array.
     * @param list - List.
     * @param rows - rows in array.
     * @return - array.
     */
    public int[][] toArray(List<Integer> list, int rows) {
        Iterator<Integer> iterator = list.iterator();
        double size = (double) list.size() / rows;
        int column = (size % (int) size) != 0 ? (int) size + 1 : (int) size;
        int[][] result = new int[rows][column];
        Stream.iterate(0, i -> i + 1)
                .limit(rows)
                .forEach(i ->
                    result[i] = Stream.iterate(0, j -> j + 1)
                            .limit(column)
                            .mapToInt(j -> iterator.hasNext() ? iterator.next() : 0)
                            .toArray()
                );
        return result;
    }

    /**
     * Convert List arrays to List.
     * @param list - List with arrays.
     * @return - List.
     */
    public List<Integer> convert(List<int[]> list) {
        return list.stream()
                .flatMapToInt(Arrays::stream)
                .boxed()
                .collect(Collectors.toList());
    }
}