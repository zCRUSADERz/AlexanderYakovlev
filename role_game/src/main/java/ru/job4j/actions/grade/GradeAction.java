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
     * Возвращает коллекцию героев относящихся к той группе,
     * над которой возможно выполнить действие.
     * Например для улучшения героя будет возвращена
     * коллекция не привилегированных героев из дружественной команды.
     * @param heroActor герой выполняющий действие.
     * @return коллекция героев относящихся к той группе,
     * над которой возможно выполнить действие.
     */
    Collection<Hero> gradedHeroes(Hero heroActor);

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
