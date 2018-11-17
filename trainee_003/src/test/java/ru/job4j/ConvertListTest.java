package ru.job4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test convert List.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.01.2017
 * @version 1.0
 */
public class ConvertListTest {
private final ConvertList convert = new ConvertList();

    @Test
    public void whenArrayToList() {
        int[][] array = new int[][]{{1}, {4}};
        List<Integer> result = this.convert.toList(array);
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 4));
        assertThat(result, is(expected));
    }

    @Test
    public void whenListToArrayAndNumberOfElementsIsNotAMultipleOfRows() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 4));
        int[][] result = this.convert.toArray(list, 3);
        int[][] expected = new int[][]{{1}, {4}, {0}};
        assertThat(result, is(expected));
    }

    @Test
    public void whenListToArrayAndNumberOfElementsAMultipleOfRows() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 4));
        int[][] result = this.convert.toArray(list, 2);
        int[][] expected = new int[][]{{1}, {4}};
        assertThat(result, is(expected));
    }

    @Test
    public void whenListArraysConvertToListInteger() {
        List<int[]> list = new ArrayList<>(
                Arrays.asList(
                        new int[]{1, 2},
                        new int[]{3, 4, 5, 6}
                        )
        );
        List<Integer> result = this.convert.convert(list);
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertThat(result, is(expected));
    }
}