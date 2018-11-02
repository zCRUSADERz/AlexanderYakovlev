package ru.job4j.xml;

import org.w3c.dom.Node;
import ru.job4j.heroes.HeroFactory;
import ru.job4j.heroes.HeroType;
import ru.job4j.races.Race;
import ru.job4j.races.RaceSimple;
import ru.job4j.xml.heroes.XMLHeroParser;
import ru.job4j.xml.heroes.types.XMLHeroType;

import javax.xml.xpath.XPath;
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
    private final Set<XMLHeroType> heroTypes;
    private final XMLHeroParser heroParser;

    public XMLRaceParserSimple(XPath xPath,
                               Set<XMLHeroType> heroTypes,
                               XMLHeroParser heroParser) {
        this.xPath = xPath;
        this.heroTypes = heroTypes;
        this.heroParser = heroParser;
    }

    /**
     * Парсит данные расы из xml документа.
     * @param nodeRace xml узел c данными расы.
     * @return расу.
     */
    @Override
    public Race parseRace(Node nodeRace) {
        try {
            final String raceName = this.xPath.evaluate("name", nodeRace);
            final Map<HeroType, HeroFactory> heroFactories = new HashMap<>();
            this.heroTypes.forEach((heroType) -> {
                Node heroNode = heroType.findHeroNode(nodeRace);
                heroFactories.put(
                        heroType,
                        this.heroParser.parseHero(heroNode)
                );
            });
            return new RaceSimple(raceName, heroFactories);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression");
        }
    }
}
