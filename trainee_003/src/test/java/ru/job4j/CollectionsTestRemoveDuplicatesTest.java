package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Remove duplicates test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 16.01.2017
 * @version 1.0
 */
public class CollectionsTestRemoveDuplicatesTest {

    @Test
    public void whenTestCollection() {
        CollectionsTestRemoveDuplicates testPerformance = new CollectionsTestRemoveDuplicates(1000, 0.1, 50);
        Object[] objects = testPerformance.getArray();
        ArrayList<Object> arrayList = new ArrayList<>(Arrays.asList(objects));
        LinkedList<Object> linkedList = new LinkedList<>(Arrays.asList(objects));
        LinkedList<Object> linkedList2 = new LinkedList<>(Arrays.asList(objects));
        System.out.println("1. ArrayList:  " + testPerformance.test(arrayList));
        System.out.println("2. LinkedList: " + testPerformance.test(linkedList2));
        System.out.println("3. LinkedList: " + testPerformance.testLinked(linkedList));
    }
}