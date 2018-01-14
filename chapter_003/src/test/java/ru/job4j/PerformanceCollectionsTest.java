package ru.job4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Testing performance collections.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 13.01.2017
 * @version 1.0
 */
public class PerformanceCollectionsTest {
    private int amount = 1000;

    @Test
    public void whenArrayListTestAddTenStringsThenListSizeTen() {
        PerformanceCollections performance = new PerformanceCollections();
        ArrayList<String> list = new ArrayList<>();
        performance.add(list, 10);
        int result = list.size();
        int expected = 10;
        assertThat(result, is(expected));
    }

    @Test
    public void whenArrayListTestDeleteOneStringOutOfTwoThenListSizeOne() {
        PerformanceCollections performance = new PerformanceCollections();
        ArrayList<String> list = new ArrayList<>(Arrays.asList("123", "234"));
        performance.delete(list, 1);
        int result = list.size();
        int expected = 1;
        assertThat(result, is(expected));
    }

    @Test
    public void testArrayListAddOneThousandStrings() {
        PerformanceCollections performance = new PerformanceCollections();
        ArrayList<String> list = new ArrayList<>();
        System.out.println("ArrayList add:                    " + performance.add(list, amount));
    }

    @Test
    public void testArrayListDeleteOneThousandStringsFromListOfTenThousandLines() {
        PerformanceCollections performance = new PerformanceCollections();
        ArrayList<String> list = new ArrayList<>();
        performance.add(list, 10000);
        System.out.println("ArrayList delete:                 " + performance.delete(list, amount));
    }

    @Test
    public void testArrayListWithSetCapacityAddOneThousandStrings() {
        PerformanceCollections performance = new PerformanceCollections();
        ArrayList<String> list = new ArrayList<>(amount);
        System.out.println("ArrayList with set capacity add:  " + performance.add(list, amount));
    }

    @Test
    public void testLinkedListAddOneThousandStrings() {
        PerformanceCollections performance = new PerformanceCollections();
        LinkedList<String> list = new LinkedList<>();
        System.out.println("LinkedList add:                   " + performance.add(list, amount));
    }

    @Test
    public void testLinkedListDeleteOneThousandStringsFromListOfTenThousandLines() {
        PerformanceCollections performance = new PerformanceCollections();
        LinkedList<String> list = new LinkedList<>();
        performance.add(list, 10000);
        System.out.println("LinkedList delete:                " + performance.delete(list, amount));
    }

    @Test
    public void testTreeSetAddOneThousandStrings() {
        PerformanceCollections performance = new PerformanceCollections();
        TreeSet<String> list = new TreeSet<>();
        System.out.println("TreeSet add:                      " + performance.add(list, amount));
    }

    @Test
    public void testTreeSetDeleteOneThousandStringsFromListOfTenThousandLines() {
        PerformanceCollections performance = new PerformanceCollections();
        TreeSet<String> list = new TreeSet<>();
        performance.add(list, 10000);
        System.out.println("TreeSet delete:                   " + performance.add(list, amount));
    }
}