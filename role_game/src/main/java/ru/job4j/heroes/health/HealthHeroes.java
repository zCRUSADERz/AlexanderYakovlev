package ru.job4j.heroes.health;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.HeroDiedObserver;
import ru.job4j.observers.HeroCreatedObserver;

/**
 * Health heroes.
 * Хранилище объектов здоровья для каждого героя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface HealthHeroes extends HeroDiedObserver, HeroCreatedObserver {

    /**
     * Герой был атакован. Здоровье атакованного героя получит переданный урон.
     * @param hero атакованный герой.
     * @param damage нанесенный урон.
     */
    void attackHero(Hero hero, int damage);
}
