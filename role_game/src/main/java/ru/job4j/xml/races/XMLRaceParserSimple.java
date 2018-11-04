package ru.job4j.xml.races;

import org.w3c.dom.Node;
import ru.job4j.heroes.HeroFactory;
import ru.job4j.races.Race;
import ru.job4j.races.RaceSimple;
import ru.job4j.xml.heroes.XMLHeroParser;
import ru.job4j.xml.heroes.types.XMLHeroType;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * XMLRaceParserSimple.
 * Парсер рас из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class XMLRaceParserSimple implements XMLRaceParser {
    private final XPath xPath;
    private final XMLHeroParser heroParser;

    public XMLRaceParserSimple(XPath xPath, XMLHeroParser heroParser) {
        this.xPath = xPath;
        this.heroParser = heroParser;
    }

    /**
     * Парсит данные расы из xml документа.
     * @param nodeRace xml узел c данными расы.
     * @param types множество всех типов героев.
     * @return расу.
     */
    @Override
    public Race parseRace(Node nodeRace, Set<XMLHeroType> types) {
        try {
            final String raceName = this.xPath.evaluate("name", nodeRace);
            final Map<XMLHeroType, HeroFactory> heroFactories = new HashMap<>();
            final Node heroes = (Node) this.xPath.evaluate(
                    "heroes", nodeRace, XPathConstants.NODE
            );
            types.forEach((heroType) -> {
                Node heroNode = heroType.findHeroNode(heroes);
                heroFactories.put(
                        heroType,
                        this.heroParser.parseHero(heroNode)
                );
            });
            return new RaceSimple(raceName, heroFactories);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression", e);
        }
    }
}
