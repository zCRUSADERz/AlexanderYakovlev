package ru.job4j.races;

import ru.job4j.heroes.Hero;
import ru.job4j.heroes.HeroType;

/**
 * Race.
 * Раса.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface Race {

    /**
     * Создать героя данной расы.
     * @param type тип героя.
     * @param squadName название отряда.
     * @return герой данной расы.
     */
    Hero createHero(HeroType type, String squadName);
}
