package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.Squad;
import ru.job4j.squad.SquadHeroes;
import ru.job4j.squad.SquadSubType;
import ru.job4j.utils.RandomElementFromList;

import java.util.List;
import java.util.Optional;

public class RandomTargetImpl implements RandomTarget {
    private final Squad squad;
    private final RandomElementFromList random;

    public RandomTargetImpl(Squad squad, RandomElementFromList random) {
        this.squad = squad;
        this.random = random;
    }

    @Override
    public Optional<Hero> target(SquadSubType squadType) {
        final Optional<Hero> result;
        final List<Hero> heroes = this.squad
                .operation(squadType)
                .heroes();
        if (heroes.isEmpty()) {
            result = Optional.empty();
        } else {
            result = Optional.of(this.random.randomElement(heroes));
        }
        return result;
    }
}
