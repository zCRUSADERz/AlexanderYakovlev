package ru.job4j.tree;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenNewTreeThenIteratorHasNextReturnTrue() {
        Tree<Integer> tree = new Tree<>(2);
        boolean result = tree.iterator().hasNext();
        assertThat(result, is(true));
    }

    @Test
    public void whenNewTreeThenIteratorNextReturnRootVlue() {
        Tree<Integer> tree = new Tree<>(3);
        Integer result = tree.iterator().next();
        assertThat(result, is(3));
    }

    @Test
    public void whenTreeHave6ElThenIteratorReturnThisEl() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 6);
        tree.add(4, 5);
        Iterator<Integer> it = tree.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(5));
    }

    @Test (expected = ConcurrentModificationException.class)
    public void whenChangeTreeThenHasNextReturnException() {
        Tree<Integer> tree = new Tree<>(1);
        Iterator<Integer> it = tree.iterator();
        tree.add(1, 2);
        it.hasNext();
    }

    @Test (expected = ConcurrentModificationException.class)
    public void whenChangeTreeThenNextReturnException() {
        Tree<Integer> tree = new Tree<>(1);
        Iterator<Integer> it = tree.iterator();
        tree.add(1, 2);
        it.next();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void whenIteratorRemoveThenThrowException() {
        Tree<Integer> tree = new Tree<>(1);
        Iterator<Integer> it = tree.iterator();
        it.remove();
    }

    @Test (expected = NoSuchElementException.class)
    public void whenTreeEmptyThenIteratorNextThrowException() {
        Tree<Integer> tree = new Tree<>(1);
        Iterator<Integer> it = tree.iterator();
        it.next();
        it.next();

    }

    @Test
    public void whenTreeIsBinaryThenIsBinaryReturnTrue() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(3, 5);
        tree.add(3, 6);
        tree.add(6, 7);
        boolean result = tree.isBinary();
        assertThat(result, is(true));
    }

    @Test
    public void whenTreeNotBinaryThenIsBinaryReturnFalse() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(3, 5);
        tree.add(3, 6);
        tree.add(6, 7);
        tree.add(6, 8);
        tree.add(6, 9);
        boolean result = tree.isBinary();
        assertThat(result, is(false));
    }
}