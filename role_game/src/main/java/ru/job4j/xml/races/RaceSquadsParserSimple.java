package ru.job4j.xml.races;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.job4j.races.Race;
import ru.job4j.xml.heroes.types.XMLHeroType;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.Random;
import java.util.Set;

/**
 * RaceSquadsParserSimple.
 * Парсер отрядов. Парсит рандомную расу из выбранного отряда.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class RaceSquadsParserSimple implements RaceSquadsParser {
    private final XPath xPath;
    private final XMLRaceParser raceParser;
    private final Random random;

    public RaceSquadsParserSimple(XPath xPath,
                                  XMLRaceParser raceParser) {
        this(xPath, raceParser, new Random());
    }

    public RaceSquadsParserSimple(XPath xPath,
                                  XMLRaceParser raceParser,
                                  Random random) {
        this.xPath = xPath;
        this.raceParser = raceParser;
        this.random = random;
    }

    /**
     * Парсит рандомную расу из выбранного отряда.
     * @param document xml документ с описанием рас.
     * @param squadIndex индекс отряда из которого будет выбрана рандомная раса.
     * @param heroTypes множество всех типов героев.
     * @return рандомная раса из выбранного отряда.
     */
    @Override
    public Race parseRandomRace(
            Document document, int squadIndex, Set<XMLHeroType> heroTypes) {
        try {
            final NodeList races = (NodeList) this.xPath.evaluate(
                    createExpression(squadIndex),
                    document,
                    XPathConstants.NODESET
            );
            int randomIndex = random.nextInt(races.getLength());
            return this.raceParser.parseRace(
                    races.item(randomIndex),
                    heroTypes
            );
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(
                    String.format(
                            "Wrong XPath expression: %s",
                            createExpression(squadIndex)
                    ), e
            );
        }
    }

    private String createExpression(int index) {
        return String.format("/configuration/squad[%d]/*", index);
    }
}
