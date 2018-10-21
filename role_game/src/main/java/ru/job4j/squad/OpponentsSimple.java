package ru.job4j.squad;

import ru.job4j.StopGame;
import ru.job4j.heroes.HeroType;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.races.Race;
import ru.job4j.utils.RandomElementFromList;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Opponents.
 * Противоборствующие стороны. Две расы воюющих друг с другом.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class OpponentsSimple implements Opponents {
    private final Race firstRace;
    private final Race secondRace;
    private final GradeChangeObservable upgradeObservable;
    private final SquadsMapper squadsMapper;
    private final StopGame stopGame;
    private final RandomElementFromList random;

    public OpponentsSimple(Race firstRace, Race secondRace,
                           GradeChangeObservable upgradeObservable,
                           SquadsMapper squadsMapper,
                           StopGame stopGame, RandomElementFromList random) {
        this.firstRace = firstRace;
        this.secondRace = secondRace;
        this.upgradeObservable = upgradeObservable;
        this.squadsMapper = squadsMapper;
        this.stopGame = stopGame;
        this.random = random;
    }

    /**
     * Создать отряды противоборствующих сторон, подготовить к бою.
     * @param numberOfHeroesByType количество героев каждого типа.
     */
    @Override
    public void createSquads(Map<HeroType, Integer> numberOfHeroesByType) {
        final String firstSquadName = "Красные";
        final SquadHeroes firstSquad = this.createSquad(firstSquadName);
        final String secondSquadName = "Синие";
        final SquadHeroes secondSquad = this.createSquad(secondSquadName);
        numberOfHeroesByType.forEach((type, number) ->
                Stream.iterate(1, n -> n + 1)
                        .limit(number)
                        .forEach((n) -> {
                            squadsMapper.newHeroCreated(
                                    firstRace.createHero(type, firstSquadName),
                                    firstSquad, secondSquad
                            );
                            squadsMapper.newHeroCreated(
                                    secondRace.createHero(type, secondSquadName),
                                    secondSquad, firstSquad
                            );
                        })
        );
    }

    private SquadHeroes createSquad(String squadName) {
        return new SquadSimple(
                squadName,
                this.upgradeObservable,
                this.random,
                this.stopGame);
    }
}
