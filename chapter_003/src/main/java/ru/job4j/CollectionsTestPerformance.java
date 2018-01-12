package ru.job4j;

import java.util.*;

public class CollectionsTestPerformance {
    private int count;
    private double percent;
    private int numOfDuplicate;
    private int countTest = 1;


    private CollectionsTestPerformance(int count, double percent, int numOfDuplicate) {
        this.count = count;
        this.percent = percent;
        this.numOfDuplicate = numOfDuplicate;
    }

    public static void main(String[] args) {
        CollectionsTestPerformance testPerformance = new CollectionsTestPerformance(50000, 0.1, 1000);
        Object[] objects = testPerformance.getArray();
        ArrayList<Object> arrayList = new ArrayList<>(Arrays.asList(objects));
        LinkedList<Object> linkedList = new LinkedList<>(Arrays.asList(objects));
        testPerformance.testLinked(linkedList);
        testPerformance.test(arrayList);
    }

    private void test(List<Object> list) {
        int size = list.size() - 1;
        long start = System.nanoTime();
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
        System.out.println(countTest++ + ": " + (System.nanoTime() - start) / 1000000);
    }

    private void testLinked(LinkedList<Object> list) {
        int size = list.size() - 1;
        long start = System.nanoTime();
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
        System.out.println(countTest++ + ": " + (System.nanoTime() - start) / 1000000);
    }

    private Object[] getArray() {
        Object[] objects = new Object[count];
        for (int i = 0 ; i < count ; i++ ) {
            objects[i] = new Object();
        }
        int num = (int)(count * percent / numOfDuplicate);
        for (int j = 0; j < numOfDuplicate; j++) {
            Object o = new Object();
            for (int i = 0; i < num; i++) {
                objects[(int)(Math.random() * count)] = o;
            }
        }
        return objects;
    }
}
