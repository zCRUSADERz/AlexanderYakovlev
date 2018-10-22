package ru.job4j.actions.grade;

import ru.job4j.heroes.Hero;

import java.util.Collection;

/**
 * Grade action interface.
 * Действие изменяющее привилегированность.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface GradeAction {

    /**
     * Изменить привилегию выбранного героя.
     * @param gradedHero герой над которым будет выполнено действие.
     */
    void grade(Hero gradedHero);

    /**
     * Возвращает название действия.
     * @return название действия.
     */
    String actionName();
}
