package ru.job4j.tree;

public class BinaryNode<E extends Comparable<E>> {
    private BinaryNode<E> left;
    private BinaryNode<E> right;
    private final E value;

    public BinaryNode(E value) {
        this.value = value;
    }

    public void add(final E other) {
        if (other.compareTo(this.value) > 0) {
            if (right == null) {
                right = new BinaryNode<>(other);
            } else {
                right.add(other);
            }
        } else {
            if (left == null) {
                left = new BinaryNode<>(other);
            } else {
                left.add(other);
            }
        }
    }
    
    public E value() {
        return value;
    }

    public BinaryNode<E> left() {
        return left;
    }

    public BinaryNode<E> right() {
        return right;
    }
}
