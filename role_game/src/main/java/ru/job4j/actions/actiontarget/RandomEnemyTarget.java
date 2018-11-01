package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

/**
 * RandomEnemyTarget.
 * Рандомный вражеский герой для выполнения действия.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class RandomEnemyTarget implements RandomTarget {
    private final SquadsMapper squadsMapper;

    public RandomEnemyTarget(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    /**
     * Рандомный вражеский герой для выбранного героя.
     * @param hero герой для которого будет выбран вражеский герой.
     * @return вражеский герой, цель.
     */
    @Override
    public Hero randomTargetFor(Hero hero) {
        return this.squadsMapper
                .enemySquadFor(hero)
                .randomHero();
    }
}
