package ru.job4j.xml.actions.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * FindNodeByXPathSimple.
 * Находит необходимый узел в xml документе используя выражение XPath.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class FindNodeByXPathSimple implements FindNodeByXPath {
    private final XPath xPath;

    public FindNodeByXPathSimple(XPath xPath) {
        this.xPath = xPath;
    }

    /**
     * Найти узел по выражению XPath.
     * @param expression XPath выражение.
     * @param node xml документ в котором будем искать.
     * @return коллекцию найденных узлов,
     * или пустую коллекцию, если не найден ни один узел.
     */
    public Collection<Node> findNode(String expression, Node node) {
        try {
            final Collection<Node> result;
            final NodeList nodes = (NodeList) this.xPath.evaluate(
                    expression, node, XPathConstants.NODESET
            );
            if (nodes.getLength() == 0) {
                result = Collections.emptyList();
            } else {
                result = new ArrayList<>(nodes.getLength());
                for (int i = 0; i < nodes.getLength(); i++) {
                    result.add(nodes.item(i));
                }
            }
            return result;
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException(
                    String.format("%s - expression is wrong.", expression),
                    e
            );
        }
    }
}
