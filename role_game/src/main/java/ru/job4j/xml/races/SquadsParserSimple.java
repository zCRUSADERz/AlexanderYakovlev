package ru.job4j.xml.races;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.job4j.actions.actiontarget.Targets;
import ru.job4j.races.Race;
import ru.job4j.squad.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;

/**
 * SquadsParserSimple.
 * Парсер отрядов. Парсит рандомную расу из выбранного отряда.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 02.11.2018
 */
public class SquadsParserSimple implements SquadsParser {
    private final XPath xPath;
    private final XMLRandomRaceParser raceParser;
    private final Targets targets;
    private final SquadRegister register;
    private final SquadHeroesFactory heroesFactory;
    private final Logger logger = Logger.getLogger(SquadsParserSimple.class);

    public SquadsParserSimple(XPath xPath,
                              XMLRandomRaceParser raceParser,
                              Targets targets, SquadRegister register, SquadHeroesFactory heroesFactory) {
        this.xPath = xPath;
        this.raceParser = raceParser;
        this.targets = targets;
        this.register = register;
        this.heroesFactory = heroesFactory;
    }

    /**
     * Парсит рандомную расу из выбранного отряда.
     * @param document xml документ с описанием рас.
     */
    @Override
    public Squads parse(Document document) {
        try {
            this.logger.info("Creation and initialization squads and heroes.");
            final NodeList squadNodes = (NodeList) this.xPath.evaluate(
                    "/configuration/squad",
                    document,
                    XPathConstants.NODESET
            );
            final Squads squads = new Squads();
            final Squad firstSquad = this.parseSquad(squadNodes.item(0));
            squads.newSquad(firstSquad);
            final Squad secondSquad = this.parseSquad(squadNodes.item(1));
            squads.newSquad(secondSquad);
            this.targets.newSquads(firstSquad, secondSquad);
            this.logger.info("Squads and heroes created and initialized.");
            return squads;
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression", e);
        }
    }

    private Squad parseSquad(Node squadNode) {
        try {
            final String squadName
                    = this.xPath.evaluate("name", squadNode);
            this.logger.info(String.format("Parse squad: %s.", squadName));
            final Race race = this.raceParser.parse(squadNode);
            final Map<SquadSubType, SquadOperation> operationMap = new HashMap<>();
            for (SquadSubType type : SquadSubType.values()) {
                operationMap.put(
                        type,
                        new SquadOperation(squadName + type)
                );
            }
            final Squad squad = new Squad(squadName, operationMap);
            this.register.register(squad);
            this.heroesFactory.createSquad(race, squad);
            return squad;
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Wrong XPath expression", e);
        }
    }
}
