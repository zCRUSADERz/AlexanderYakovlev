package ru.job4j.xml.actions.utils;

import org.w3c.dom.Node;

import java.util.Collection;

/**
 * FindNodeByXPath.
 * Находит необходимый узел в xml документе используя выражение XPath.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public interface FindNodeByXPath {

    /**
     * Найти узел по выражению XPath.
     * @param expression XPath выражение.
     * @param node xml документ в котором будем искать.
     * @return коллекцию найденных узлов,
     * или пустую коллекцию, если не найден ни один узел.
     */
    Collection<Node> find(String expression, Node node);
}
