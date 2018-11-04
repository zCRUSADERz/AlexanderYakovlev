package ru.job4j.squad;

import org.w3c.dom.Document;
import ru.job4j.GameEnvironment;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.races.Race;
import ru.job4j.xml.NumberOfHeroesParser;
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
    @Override
    public void createSquads(Document document) {
        final Set<XMLHeroType> heroTypes = this.typesParser.parseTypes(document);
        final Race raceOfRed = this.squadsParser.parseRandomRace(document, 1, heroTypes);
        final String firstSquadName = "Красные";
        final SquadHeroes firstSquad = this.createSquad(firstSquadName);
        final Race raceOfBlue = this.squadsParser.parseRandomRace(document, 2, heroTypes);
        final String secondSquadName = "Синие";
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
    }

    private SquadHeroes createSquad(String squadName) {
        return new SquadSimple(
                squadName,
                this.upgradeObservable,
                this.environment.getRandom(),
                this.environment.getStopGame());
    }
}
