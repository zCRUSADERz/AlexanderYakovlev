package ru.job4j.list;

class Node<E> {
    E value;
    Node<E> next;

    Node(E value, Node<E> next) {
        this.value = value;
        this.next = next;
    }
}
