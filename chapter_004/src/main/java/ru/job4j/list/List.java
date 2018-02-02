package ru.job4j.list;

public class List {

    boolean hasCycle(Node first) {
        boolean result = false;
        Node<Node> reverse = new Node<>(first, null);
        while (first.next != null) {
            first = first.next;
            Node<Node> node = new Node<>(first, reverse);
            reverse = node;
            while (node.next != null) {
                node = node.next;
                if (node.value == first) {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }
}
