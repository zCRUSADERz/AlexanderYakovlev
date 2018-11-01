package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

/**
 * RandomTargetForDegrade.
 * Рандомный герой(цель) для снятия улучшения.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class RandomTargetForDegrade implements RandomTargetForGrade {
    private final SquadsMapper squadsMapper;

    public RandomTargetForDegrade(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    /**
     * Проверяет есть ли цели для выбранного героя.
     * Для снятия улучшения нужны привилегированные герои.
     * @param hero герой для которого будет проверено наличие целей.
     * @return true если есть подходящие цели.
     */
    @Override
    public boolean targetsIsEmptyFor(Hero hero) {
        return this.squadsMapper
                .enemySquadFor(hero)
                .upgradedHeroesIsEmpty();
    }

    /**
     * Рандомная цель для выбранного героя.
     * @param hero герой для которого будет выбрана цель.
     * @return цель.
     */
    @Override
    public Hero randomTargetFor(Hero hero) {
        return this.squadsMapper
                .enemySquadFor(hero)
                .randomUpgradedHero();
    }
}
