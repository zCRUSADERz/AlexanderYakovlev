package ru.job4j.tree;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BinarySearchTreeTest {

    @Test
    public void whenAdd7ElThenIterableAll() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(5);
        bst.add(4);
        bst.add(6);
        bst.add(6);
        bst.add(-10);
        bst.add(10);
        bst.add(3);
        bst.add(1);
        Iterator<Integer> it = bst.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(-10));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(10));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(false));
    }

    @Test (expected = ConcurrentModificationException.class)
    public void whenChangeTreeThenHasNextReturnException() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(1);
        Iterator<Integer> it = tree.iterator();
        tree.add(1);
        it.hasNext();
    }

    @Test (expected = ConcurrentModificationException.class)
    public void whenChangeTreeThenNextReturnException() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(1);
        Iterator<Integer> it = tree.iterator();
        tree.add(1);
        it.next();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void whenIteratorRemoveThenThrowException() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(1);
        Iterator<Integer> it = tree.iterator();
        it.remove();
    }

    @Test (expected = NoSuchElementException.class)
    public void whenTreeEmptyThenIteratorNextThrowException() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(1);
        Iterator<Integer> it = tree.iterator();
        it.next();
        it.next();

    }
}