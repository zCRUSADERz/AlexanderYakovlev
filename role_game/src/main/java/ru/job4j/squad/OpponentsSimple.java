package ru.job4j.squad;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import ru.job4j.GameEnvironment;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.races.Race;
import ru.job4j.xml.heroes.NumberOfHeroesParser;
import ru.job4j.xml.heroes.types.HeroTypesParser;
import ru.job4j.xml.heroes.types.XMLHeroType;
import ru.job4j.xml.races.RaceSquadsParser;

import java.util.Set;
import java.util.stream.Stream;

/**
 * Opponents.
 * Противоборствующие стороны. Две расы воюющих друг с другом.
 * Класс отвечает за создание противоборствующих отрядов и героев в них.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class OpponentsSimple implements Opponents {
    private final RaceSquadsParser squadsParser;
    private final NumberOfHeroesParser numberOfHeroesParser;
    private final HeroTypesParser typesParser;
    private final GradeChangeObservable upgradeObservable;
    private final GameEnvironment environment;
    private final Logger logger = Logger.getLogger(OpponentsSimple.class);

    public OpponentsSimple(RaceSquadsParser squadsParser,
                           NumberOfHeroesParser numberOfHeroesParser,
                           HeroTypesParser typesParser,
                           GradeChangeObservable upgradeObservable,
                           GameEnvironment environment) {
        this.squadsParser = squadsParser;
        this.numberOfHeroesParser = numberOfHeroesParser;
        this.typesParser = typesParser;
        this.upgradeObservable = upgradeObservable;
        this.environment = environment;
    }

    /**
     * Создать противоборствующие отряды, подготовить к бою.
     * @param document xml документ с описанием отрядов.
     */
    //TODO Повторяющийся код для создания отряда, нужно исправить.
    @Override
    public void createSquads(Document document) {
        this.logger.info("Creation and initialization squads and heroes.");
        final Set<XMLHeroType> heroTypes = this.typesParser.parseTypes(document);
        this.logger.info(String.format("Parse hero types: %s", heroTypes));
        final int firstSquadIndex = 1;
        final String firstSquadName = "Красные";
        this.logger.info(String.format(
                "Parse squad: %s, squad index: %d",
                firstSquadName,
                firstSquadIndex
        ));
        final Race raceOfRed = this.squadsParser
                .parseRandomRace(document, firstSquadIndex, heroTypes);
        final SquadHeroes firstSquad = this.createSquad(firstSquadName);
        final int secondSquadIndex = 2;
        final String secondSquadName = "Синие";
        this.logger.info(String.format(
                "Parse squad: %s, squad index: %d",
                secondSquadName, secondSquadIndex
        ));
        final Race raceOfBlue = this.squadsParser
                .parseRandomRace(document, secondSquadIndex, heroTypes);
        final SquadHeroes secondSquad = this.createSquad(secondSquadName);
        final SquadsMapper squadsMapper = this.environment.getSquadsMapper();
        this.numberOfHeroesParser
                .parseNumberOfHeroes(document, heroTypes)
                .forEach((type, number) ->
                        Stream.iterate(1, n -> n + 1)
                                .limit(number)
                        .forEach((n) -> {

                            squadsMapper.newHeroCreated(
                                    raceOfRed.createHero(type, firstSquadName),
                                    firstSquad, secondSquad
                            );
                            squadsMapper.newHeroCreated(
                                    raceOfBlue.createHero(type, secondSquadName),
                                    secondSquad, firstSquad
                            );
                        })
        );
        this.logger.info("Squads and heroes created and initialized.");
    }

    private SquadHeroes createSquad(String squadName) {
        return new SquadSimple(
                squadName,
                this.upgradeObservable,
                this.environment.getRandom(),
                this.environment.getStopGame());
    }
}
