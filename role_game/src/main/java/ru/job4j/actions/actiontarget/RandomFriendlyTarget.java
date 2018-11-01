package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

/**
 * RandomFriendlyTarget.
 * Рандомный дружественный герой для выполнения действия.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class RandomFriendlyTarget implements RandomTarget {
    private final SquadsMapper squadsMapper;

    public RandomFriendlyTarget(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    /**
     * Рандомный дружественный герой для выбранного героя.
     * @param hero герой для которого будет выбран дружественный герой.
     * @return дружественный герой, цель.
     */
    @Override
    public Hero randomTargetFor(Hero hero) {
        return this.squadsMapper
                .ownSquadFor(hero)
                .randomHero();
    }
}
