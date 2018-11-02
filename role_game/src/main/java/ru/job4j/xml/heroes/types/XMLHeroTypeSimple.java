package ru.job4j.xml.heroes.types;

import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.Objects;

/**
 * XMLHeroTypeSimple.
 * Тип героя(Например: маг, лучник, воин и др.).
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class XMLHeroTypeSimple implements XMLHeroType {
    private final XPath xPath;
    private final String typeName;

    public XMLHeroTypeSimple(XPath xPath, String typeName) {
        this.xPath = xPath;
        this.typeName = typeName;
    }

    /**
     * Находит узел содержащий данные героя этого типа.
     * @param heroes узел указывающий на всех героев.
     * @return узел содержащий данные героя этого типа.
     */
    @Override
    public Node findHeroNode(Node heroes) {
        try {
            return (Node) this.xPath.evaluate(
                    "heroes/" + typeName, heroes, XPathConstants.NODE
            );
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(String.format(
                    "Hero type name: %s, is wrong XPath expression",
                    this.typeName
            ));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        XMLHeroTypeSimple that = (XMLHeroTypeSimple) o;
        return Objects.equals(xPath, that.xPath)
                && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPath, typeName);
    }
}
