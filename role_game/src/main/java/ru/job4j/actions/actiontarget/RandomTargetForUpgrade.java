package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

import java.util.Optional;

/**
 * RandomTargetForUpgrade.
 * Рандомный герой(цель) для улучшения.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class RandomTargetForUpgrade implements RandomTarget {
    private final SquadsMapper squadsMapper;

    public RandomTargetForUpgrade(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    /**
     * Рандомная цель для выбранного героя.
     * @param hero герой для которого будет выбрана цель.
     * @return цель.
     */
    @Override
    public Optional<Hero> randomTargetFor(Hero hero) {
        return this.squadsMapper
                .ownSquadFor(hero)
                .randomRegularHero();
    }
}
