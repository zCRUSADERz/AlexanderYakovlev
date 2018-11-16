package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ListTest {

    @Test
    public void whenCycleListThenHasCycleReturnTrue() {
        Node<Integer> first = new Node<>(1, null);
        Node<Integer> two = new Node<>(2, null);
        Node<Integer> third = new Node<>(3, null);
        Node<Integer> four = new Node<>(4, two);

        first.next = two;
        two.next = third;
        third.next = four;

        boolean result = new List().hasCycle(first);
        assertThat(result, is(true));
    }

    @Test
    public void whenNotCycledThenHasCycleReturnFalse() {
        Node<Integer> first = new Node<>(1, null);

        boolean result = new List().hasCycle(first);
        assertThat(result, is(false));
    }
}