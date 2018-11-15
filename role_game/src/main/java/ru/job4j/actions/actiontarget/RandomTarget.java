package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadSubType;

import java.util.Optional;

public interface RandomTarget {

    Optional<Hero> target(SquadSubType squadType);
}
