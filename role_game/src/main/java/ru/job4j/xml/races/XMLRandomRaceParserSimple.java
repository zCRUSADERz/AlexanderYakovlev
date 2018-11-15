package ru.job4j.xml.races;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.job4j.heroes.HeroFactory;
import ru.job4j.races.Race;
import ru.job4j.races.RaceSimple;
import ru.job4j.xml.heroes.XMLHeroParser;
import ru.job4j.xml.heroes.types.HeroTypesParser;
import ru.job4j.xml.heroes.types.XMLHeroType;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * XMLRandomRaceParserSimple.
 * Парсер рандомной расы из xml документа.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class XMLRandomRaceParserSimple implements XMLRandomRaceParser {
    private final XPath xPath;
    private final XMLHeroParser heroParser;
    private final HeroTypesParser typesParser;
    private final Random random;
    private final Logger logger = Logger.getLogger(XMLRandomRaceParserSimple.class);

    public XMLRandomRaceParserSimple(XPath xPath,
                                     XMLHeroParser heroParser,
                                     HeroTypesParser typesParser, Random random) {
        this.xPath = xPath;
        this.heroParser = heroParser;
        this.typesParser = typesParser;
        this.random = random;
    }

    /**
     * Парсит равндомную расу из xml документа.
     * @param squad xml узел c данными расы.
     * @return расу.
     */
    @Override
    public Race parse(Node squad) {
        try {
            final NodeList races = (NodeList) this.xPath.evaluate(
                    "race",
                    squad,
                    XPathConstants.NODESET
            );
            final Node race = races.item(
                    random.nextInt(
                            races.getLength()
                    )
            );
            final String raceName = this.xPath.evaluate("name", race);
            this.logger.info(String.format("    Race name: %s", raceName));
            final Map<XMLHeroType, HeroFactory> heroFactories = new HashMap<>();
            final Node heroes = (Node) this.xPath.evaluate(
                    "heroes", race, XPathConstants.NODE
            );
            typesParser.parse().forEach((heroType) -> {
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
