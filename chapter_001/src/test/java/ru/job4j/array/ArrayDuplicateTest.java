package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * Array duplicate test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class ArrayDuplicateTest {

    @Test
    public void whenArrayHasDuplicatesTheDropIt() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] input = {"Sasha", "Olga", "Sasha", "Sergey", "Olga", "Andrey"};
        String[] result = arrayDuplicate.remove(input);
        String[] expected = {"Sasha", "Olga", "Andrey", "Sergey"};
        assertThat(result, arrayContainingInAnyOrder(expected));
    }
}
