package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;

import java.util.Optional;

/**
 * RandomTarget.
 * Рандомный герой(цель) для выполнения действия.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface RandomTarget {

    /**
     * Рандомный герой(цель) для выбранного героя.
     * @param hero герой для которого будет выбрана цель.
     * @return цель.
     */
    Optional<Hero> randomTargetFor(Hero hero);
}
