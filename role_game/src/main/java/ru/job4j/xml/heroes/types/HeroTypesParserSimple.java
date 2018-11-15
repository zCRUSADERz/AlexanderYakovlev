package ru.job4j.xml.heroes.types;

import org.apache.log4j.Logger;
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
    private final Document document;
    private final Logger logger = Logger.getLogger(HeroTypesParserSimple.class);

    public HeroTypesParserSimple(XPath xPath, Document document) {
        this.xPath = xPath;
        this.document = document;
    }

    /**
     * Парсит все типы героев.
     * @return типы героев.
     */
    @Override
    public Set<XMLHeroType> parse() {
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
            this.logger.info(String.format("HeroTypesParser - parse hero types: %s", types));
            return types;
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression.", e);
        }
    }
}
