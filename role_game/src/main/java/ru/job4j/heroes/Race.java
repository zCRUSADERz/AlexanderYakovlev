package ru.job4j.heroes;

import ru.job4j.squad.SquadHeroes;

public interface Race {

    void createMagiciansHeroes(int numberOfHeroes, SquadHeroes ownSquad, SquadHeroes enemySquad);

    void createArchersHeroes(int numberOfHeroes, SquadHeroes ownSquad, SquadHeroes enemySquad);

    void createWarriorsHeroes(int numberOfHeroes, SquadHeroes ownSquad, SquadHeroes enemySquad);
}
