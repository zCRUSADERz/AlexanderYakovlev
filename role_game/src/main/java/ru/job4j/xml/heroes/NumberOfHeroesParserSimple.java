package ru.job4j.xml.heroes;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import ru.job4j.xml.heroes.types.HeroTypesParser;
import ru.job4j.xml.heroes.types.XMLHeroType;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;

/**
 * NumberOfHeroesParser.
 * Парсер количества героев каждого типа в отряде.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class NumberOfHeroesParserSimple implements NumberOfHeroesParser {
    private final XPath xPath;
    private final Document document;
    private final HeroTypesParser typesParser;
    private final Logger logger = Logger.getLogger(NumberOfHeroesParserSimple.class);

    public NumberOfHeroesParserSimple(XPath xPath,
                                      Document document,
                                      HeroTypesParser typesParser) {
        this.xPath = xPath;
        this.document = document;
        this.typesParser = typesParser;
    }

    /**
     * Возвращает словарь, в котором каждому типу героя
     * соответствует количество героев в отряде данного типа.
     * @return словарь, в котором каждому типу героя
     * соответствует количество героев в отряде данного типа.
     */
    @Override
    public Map<XMLHeroType, Integer> parse() {
        try {
            final Map<XMLHeroType, Integer> numberOfHeroes = new HashMap<>();
            this.logger.info("NumberOfHeroesParser - parse squad size.");
            final Node squadSize = (Node) this.xPath.evaluate(
                    "/configuration/squadSize",
                    this.document,
                    XPathConstants.NODE
            );
            this.typesParser.parse().forEach((heroType) -> {
                try {
                    Node heroNode = heroType.findHeroNode(squadSize);
                    int number = Integer.parseInt(
                            this.xPath.evaluate("size", heroNode)
                    );
                    numberOfHeroes.put(heroType, number);
                    this.logger.info(String.format(
                            "For type: %s - %d heroes.",
                            heroType, number
                    ));
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                    });
            return numberOfHeroes;
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression", e);
        }
    }
}
