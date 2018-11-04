package ru.job4j.xml.heroes.types;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashSet;
import java.util.Set;

/**
 * HeroTypesParserSimple.
 * Парсер всех типов героев.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class HeroTypesParserSimple implements HeroTypesParser {
    private final XPath xPath;

    public HeroTypesParserSimple(XPath xPath) {
        this.xPath = xPath;
    }

    /**
     * Парсит все типы героев.
     * @param document xml документ содержащий описание типов героев.
     * @return типы героев.
     */
    @Override
    public Set<XMLHeroType> parseTypes(Document document) {
        try {
            final NodeList heroTypes = (NodeList) xPath.evaluate(
                    "/configuration/heroTypes/*", document, XPathConstants.NODESET
            );
            final Set<XMLHeroType> types = new HashSet<>();
            for (int i = 0; i < heroTypes.getLength(); i++) {
                types.add(
                        new XMLHeroTypeSimple(
                                this.xPath,
                                heroTypes.item(i).getNodeName()
                        )
                );
            }
            return types;
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression.", e);
        }
    }
}
