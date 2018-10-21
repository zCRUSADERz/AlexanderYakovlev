package ru.job4j.heroes.health;

import ru.job4j.heroes.Hero;

/**
 * Hero health.
 * Здоровье героя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface HeroHealth {

    /**
     * Получить урон.
     * @param heroOwner герой владелец здоровья.
     * @param damage полученный урон.
     */
    void takeDamage(Hero heroOwner, int damage);
}
