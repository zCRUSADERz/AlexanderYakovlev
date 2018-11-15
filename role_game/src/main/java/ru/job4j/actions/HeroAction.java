package ru.job4j.actions;

import ru.job4j.heroes.Hero;

/**
 * Hero action.
 * Действие героя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface HeroAction {

    /**
     * Выполнить действие.
     */
    void act(Hero heroActor);
}
