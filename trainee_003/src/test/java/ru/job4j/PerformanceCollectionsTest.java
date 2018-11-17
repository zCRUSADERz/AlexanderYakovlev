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
    private final PerformanceCollections performance = new PerformanceCollections();
    private final int amount = 1000;

    @Test
    public void whenArrayListTestAddTenStringsThenListSizeTen() {
        ArrayList<String> list = new ArrayList<>();
        this.performance.add(list, 10);
        int result = list.size();
        int expected = 10;
        assertThat(result, is(expected));
    }

    @Test
    public void whenArrayListTestDeleteOneStringOutOfTwoThenListSizeOne() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("123", "234"));
        this.performance.delete(list, 1);
        int result = list.size();
        int expected = 1;
        assertThat(result, is(expected));
    }

    @Test
    public void testArrayListAddOneThousandStrings() {
        ArrayList<String> list = new ArrayList<>();
        System.out.println(String.format(
                "ArrayList add:%28s",
                this.performance.add(list, this.amount)
                )
        );
    }

    @Test
    public void testArrayListDeleteOneThousandStringsFromListOfTenThousandLines() {
        ArrayList<String> list = new ArrayList<>();
        this.performance.add(list, 10000);
        System.out.println(String.format(
                "ArrayList delete:%25s",
                this.performance.delete(list, this.amount)
                )
        );
    }

    @Test
    public void testArrayListWithSetCapacityAddOneThousandStrings() {
        ArrayList<String> list = new ArrayList<>(this.amount);
        System.out.println(String.format(
                "ArrayList with set capacity add:%10s",
                performance.add(list, amount)
                )
        );
    }

    @Test
    public void testLinkedListAddOneThousandStrings() {
        LinkedList<String> list = new LinkedList<>();
        System.out.println(String.format(
                "LinkedList add:%27s",
                this.performance.add(list, this.amount)
                )
        );
    }

    @Test
    public void testLinkedListDeleteOneThousandStringsFromListOfTenThousandLines() {
        LinkedList<String> list = new LinkedList<>();
        this.performance.add(list, 10000);
        System.out.println(String.format(
                "LinkedList delete:%24s",
                this.performance.delete(list, this.amount)
                )
        );
    }

    @Test
    public void testTreeSetAddOneThousandStrings() {
        TreeSet<String> list = new TreeSet<>();
        System.out.println(String.format(
                "TreeSet add:%30s",
                this.performance.add(list, this.amount)
                )
        );
    }

    @Test
    public void testTreeSetDeleteOneThousandStringsFromListOfTenThousandLines() {
        TreeSet<String> list = new TreeSet<>();
        this.performance.add(list, 10000);
        System.out.println(String.format(
                "TreeSet delete:%27s",
                this.performance.delete(list, this.amount)
                )
        );
    }
}