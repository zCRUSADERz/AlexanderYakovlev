package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;
import ru.job4j.squad.SquadsMapper;

/**
 * RandomTargetForUpgrade.
 * Рандомный герой(цель) для улучшения.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class RandomTargetForUpgrade implements RandomTargetForGrade {
    private final SquadsMapper squadsMapper;

    public RandomTargetForUpgrade(SquadsMapper squadsMapper) {
        this.squadsMapper = squadsMapper;
    }

    /**
     * Проверяет есть ли цели для выбранного героя.
     * Для улучшения нужны обычные герои.
     * @param hero герой для которого будет проверено наличие целей.
     * @return true если есть подходящие цели.
     */
    @Override
    public boolean targetsIsEmptyFor(Hero hero) {
        return this.squadsMapper
                .ownSquadFor(hero)
                .regularHeroesIsEmpty();
    }

    /**
     * Рандомная цель для выбранного героя.
     * @param hero герой для которого будет выбрана цель.
     * @return цель.
     */
    @Override
    public Hero randomTargetFor(Hero hero) {
        return this.squadsMapper.ownSquadFor(hero).randomRegularHero();
    }
}
