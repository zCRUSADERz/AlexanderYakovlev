package ru.job4j.heroes;

import ru.job4j.squad.SquadHeroes;
import ru.job4j.squad.Squads;

import java.util.stream.Stream;

public class RaceSimple implements Race {
    private final HeroFactory magiciansFactory;
    private final HeroFactory archersFactory;
    private final HeroFactory warriorsFactory;
    private final Squads squads;

    public RaceSimple(HeroFactory magiciansFactory, HeroFactory archersFactory,
                      HeroFactory warriorsFactory, Squads squads) {
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

    private void createHeroes(int numOfHeroes, HeroFactory heroFactory,
                              SquadHeroes ownSquad, SquadHeroes enemySquad) {
        Stream.iterate(1, n -> n + 1)
                .limit(numOfHeroes)
                .map(n -> heroFactory.hero(ownSquad.toString()))
                .forEach(hero -> squads.newHeroCreated(hero, ownSquad, enemySquad));
    }
}
