package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadSubType;

import java.util.*;

public class RandomTargetForHeroImpl implements RandomTargetForHero {
    private final TargetTypes targetType;
    private final SquadSubType squadType;
    private final Targets targets;

    public RandomTargetForHeroImpl(TargetTypes targetType,
                                   SquadSubType squadType,
                                   Targets targets) {
        this.targetType = targetType;
        this.squadType = squadType;
        this.targets = targets;
    }

    @Override
    public Optional<Hero> targetFor(Hero hero) {
        return this.targets.target(this.targetType, this.squadType, hero);
    }
}
