package ru.job4j.actions;

import ru.job4j.SquadHeroes;
import ru.job4j.heroes.health.HealthHeroes;

public interface ActionFactory {

    HeroAction action(SquadHeroes enemySquad, HealthHeroes healthHeroes);
}
