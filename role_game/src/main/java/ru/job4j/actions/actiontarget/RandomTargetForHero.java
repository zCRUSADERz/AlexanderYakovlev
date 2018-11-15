package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;

import java.util.Optional;

public interface RandomTargetForHero {

    Optional<Hero> targetFor(Hero hero);
}
