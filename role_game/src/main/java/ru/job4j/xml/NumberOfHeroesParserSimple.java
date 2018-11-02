package ru.job4j.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import ru.job4j.xml.heroes.types.XMLHeroType;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * NumberOfHeroesParser.
 * Парсер количества героев каждого типа в отряде.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class NumberOfHeroesParserSimple implements NumberOfHeroesParser {
    private final XPath xPath;
    private final Set<XMLHeroType> heroTypes;

    public NumberOfHeroesParserSimple(XPath xPath, Set<XMLHeroType> heroTypes) {
        this.xPath = xPath;
        this.heroTypes = heroTypes;
    }

    /**
     * Возвращает словарь, в котором каждому типу героя
     * соответствует количество героев в отряде данного типа.
     * @param document xml документ с описанием размера отряда.
     * @return словарь, в котором каждому типу героя
     * соответствует количество героев в отряде данного типа.
     */
    @Override
    public Map<XMLHeroType, Integer> parseNumberOfHeroes(Document document) {
        try {
            final Map<XMLHeroType, Integer> numberOfHeroes = new HashMap<>();
            final Node squadSize = (Node) this.xPath.evaluate(
                    "/configuration/squadSize", document, XPathConstants.NODE
            );
            this.heroTypes.forEach((heroType) -> {
                try {
                    Node heroNode = heroType.findHeroNode(squadSize);
                    int number = Integer.parseInt(this.xPath.evaluate("size", heroNode));
                    numberOfHeroes.put(heroType, number);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
            });
            return numberOfHeroes;
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression");
        }
    }
}
