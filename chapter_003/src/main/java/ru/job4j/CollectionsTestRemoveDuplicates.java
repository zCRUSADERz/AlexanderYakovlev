package ru.job4j;

import java.util.*;

/**
 * Testing the removal of duplicates from ArrayList and LinkedList .
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 4.01.2017
 * @version 1.0
 */
public class CollectionsTestRemoveDuplicates {
    /**
     * Amount objects.
     */
    private int amount;
    /**
     * Percentage of duplicates.
     */
    private double percent;
    /**
     *
     * Number of objects with duplicates.
     */
    private int numOfDuplicate;
    private int countTest = 1;

    /**
     * Constructor.
     * @param amount - amount objects.
     * @param percent - percentage of duplicates.
     * @param numOfDuplicate - number of objects with duplicates.
     */
    private CollectionsTestRemoveDuplicates(int amount, double percent, int numOfDuplicate) {
        this.amount = amount;
        this.percent = percent;
        this.numOfDuplicate = numOfDuplicate;
    }

    public static void main(String[] args) {
        CollectionsTestRemoveDuplicates testPerformance = new CollectionsTestRemoveDuplicates(50000, 0.1, 1000);
        Object[] objects = testPerformance.getArray();
        ArrayList<Object> arrayList = new ArrayList<>(Arrays.asList(objects));
        LinkedList<Object> linkedList = new LinkedList<>(Arrays.asList(objects));
        System.out.println("LinkedList: " + testPerformance.testLinked(linkedList));
        System.out.println("ArrayList:  " + testPerformance.test(arrayList));
    }

    /**
     * Test any List.
     * @param list - list.
     */
    private long test(List<Object> list) {
        int size = list.size() - 1;
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            ListIterator<Object> iterator = list.listIterator(i);
            Object obj = iterator.next();
            while (iterator.hasNext()) {
                if (obj.equals(iterator.next())) {
                    iterator.remove();
                    size--;
                }
            }
        }
        return System.currentTimeMillis() - start;
    }

    /**
     * Test LinkedList.
     * @param list - LinkedList
     */
    private long testLinked(LinkedList<Object> list) {
        int size = list.size() - 1;
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            Object obj = list.get(i);
            Iterator<Object> iterator = list.descendingIterator();
            int counter = list.size() - i - 1;
            while (!(--counter == 0)) {
                if (obj.equals(iterator.next())) {
                        iterator.remove();
                        size--;
                }
            }
        }
        return System.currentTimeMillis() - start;
    }

    /**
     * get Array unique objects.
     * @return - array unique objects.
     */
    private Object[] getArray() {
        Object[] objects = new Object[amount];
        for (int i = 0; i < amount; i++) {
            objects[i] = new Object();
        }
        int num = (int) (amount * percent / numOfDuplicate);
        for (int j = 0; j < numOfDuplicate; j++) {
            Object o = new Object();
            for (int i = 0; i < num; i++) {
                objects[(int) (Math.random() * amount)] = o;
            }
        }
        return objects;
    }
}
