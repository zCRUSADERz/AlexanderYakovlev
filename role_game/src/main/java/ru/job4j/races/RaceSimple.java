package ru.job4j.races;

import ru.job4j.heroes.HeroFactory;
import ru.job4j.squad.SquadHeroes;
import ru.job4j.squad.Squads;

import java.util.stream.Stream;

public class RaceSimple implements Race {
    private final String raceName;
    private final HeroFactory magiciansFactory;
    private final HeroFactory archersFactory;
    private final HeroFactory warriorsFactory;
    private final Squads squads;

    public RaceSimple(String raceName, HeroFactory magiciansFactory, HeroFactory archersFactory,
                      HeroFactory warriorsFactory, Squads squads) {
        this.raceName = raceName;
        this.magiciansFactory = magiciansFactory;
        this.archersFactory = archersFactory;
        this.warriorsFactory = warriorsFactory;
        this.squads = squads;
    }

    @Override
    public void createMagiciansHeroes(int numberOfHeroes, SquadHeroes ownSquad, SquadHeroes enemySquad) {
        createHeroes(
                numberOfHeroes,
                this.magiciansFactory,
                ownSquad,
                enemySquad
        );
    }

    @Override
    public void createArchersHeroes(int numberOfHeroes, SquadHeroes ownSquad, SquadHeroes enemySquad) {
        createHeroes(
                numberOfHeroes,
                this.archersFactory,
                ownSquad,
                enemySquad
        );
    }

    @Override
    public void createWarriorsHeroes(int numberOfHeroes, SquadHeroes ownSquad, SquadHeroes enemySquad) {
        createHeroes(
                numberOfHeroes,
                this.warriorsFactory,
                ownSquad,
                enemySquad
        );
    }

    @Override
    public String toString() {
        return this.raceName;
    }

    private void createHeroes(int numOfHeroes, HeroFactory heroFactory,
                              SquadHeroes ownSquad, SquadHeroes enemySquad) {
        Stream.iterate(1, n -> n + 1)
                .limit(numOfHeroes)
                .map(n -> heroFactory.hero(ownSquad.toString(), this.raceName))
                .forEach(hero -> squads.newHeroCreated(hero, ownSquad, enemySquad));
    }
}
