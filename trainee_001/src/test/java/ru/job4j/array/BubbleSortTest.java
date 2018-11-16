package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Bubble sort test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class BubbleSortTest {

    @Test
    public void whenSortArrayWithTenElementsThenSortedArray() {
        BubbleSort bubbleSort = new BubbleSort();
        int[] array = {1, 10, 2, 8, 3, 9, 4, 7, 5, 6};
        int[] result = bubbleSort.sort(array);
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertThat(result, is(expected));
    }
}
