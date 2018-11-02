package ru.job4j.xml.actions.utils;

import org.w3c.dom.Node;

import java.util.Collection;

public interface FindNodeByXPath {

    Collection<Node> find(String expression, Node node);
}
