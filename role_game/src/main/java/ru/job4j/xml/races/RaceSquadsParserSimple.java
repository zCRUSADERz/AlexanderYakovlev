package ru.job4j.xml.races;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.job4j.GameEnvironment;
import ru.job4j.races.Race;
import ru.job4j.races.RaceSquads;
import ru.job4j.races.RaceSquadsSimple;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

/**
 * RaceSquadsParserSimple.
 * Парсер отрядов. Парсит противоборствующие расы и создает два отряда.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class RaceSquadsParserSimple implements RaceSquadsParser {
    private final XPath xPath;
    private final XMLRaceParser raceParser;
    private final GameEnvironment environment;

    public RaceSquadsParserSimple(XPath xPath,
                                  XMLRaceParser raceParser,
                                  GameEnvironment environment) {
        this.xPath = xPath;
        this.raceParser = raceParser;
        this.environment = environment;
    }

    /**
     * Парсит расы отрядов.
     * @param document xml документ с описанием рас отрядов.
     * @return расы отрядов.
     */
    @Override
    public RaceSquads parseRaceSquads(Document document) {
        try {
            final List<Race> firstSquadRaces = parseRaces(
                    (NodeList) this.xPath.evaluate(
                            "/configuration/squad[1]/*",
                            document,
                            XPathConstants.NODESET
                    )
            );
            final List<Race> secondSquadRaces = parseRaces(
                    (NodeList) this.xPath.evaluate(
                            "/configuration/squad[2]/*",
                            document,
                            XPathConstants.NODESET
                    )
            );
            return new RaceSquadsSimple(
                    firstSquadRaces,
                    secondSquadRaces,
                    environment.getRandom()
            );
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath epression.");
        }
    }

    private List<Race> parseRaces(NodeList squad) {
        final List<Race> squadRaces = new ArrayList<>();
        for (int i = 0; i < squad.getLength(); i++) {
            squadRaces.add(raceParser.parseRace(squad.item(i)));
        }
        return squadRaces;
    }
}
