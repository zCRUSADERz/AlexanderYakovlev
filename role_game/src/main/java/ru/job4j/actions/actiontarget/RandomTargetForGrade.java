package ru.job4j.actions.actiontarget;

import ru.job4j.heroes.Hero;

/**
 * RandomTargetForGrade.
 * Рандомный герой(цель) для изменения привилегий.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface RandomTargetForGrade extends RandomTarget {

    /**
     * Проверяет есть ли цели для выбранного героя.
     * @param hero герой для которого будет проверено наличие целей.
     * @return true если есть подходящие цели.
     */
    boolean targetsIsEmptyFor(Hero hero);
}
