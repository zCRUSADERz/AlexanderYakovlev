package ru.job4j;

import java.util.ArrayList;
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

    @Test
    public void whenArrayToList() {
        ConvertList convert = new ConvertList();
        int[][] array = new int[][]{{1},{4}};
        List<Integer> result = convert.toList(array);
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(1);
        expected.add(4);
        assertThat(result, is(expected));
    }

    @Test
    public void whenListToArrayAndNumberOfElementsIsNotAMultipleOfRows() {
        ConvertList convert = new ConvertList();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(4);
        int[][] result = convert.toArray(list, 3);
        int[][] expected = new int[][]{{1}, {4}, {0}};
        assertThat(result, is(expected));
    }

    @Test
    public void whenListToArrayAndNumberOfElementsAMultipleOfRows() {
        ConvertList convert = new ConvertList();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(4);
        int[][] result = convert.toArray(list, 2);
        int[][] expected = new int[][]{{1}, {4}};
        assertThat(result, is(expected));
    }
}