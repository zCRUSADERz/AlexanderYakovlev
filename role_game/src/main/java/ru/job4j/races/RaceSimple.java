package ru.job4j.races;

import ru.job4j.heroes.Hero;
import ru.job4j.heroes.HeroFactory;
import ru.job4j.heroes.HeroType;

import java.util.Map;

/**
 * Race.
 * Раса.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class RaceSimple implements Race {
    private final String raceName;
    private final Map<HeroType, HeroFactory> factories;

    public RaceSimple(String raceName, Map<HeroType, HeroFactory> factories) {
        this.raceName = raceName;
        this.factories = factories;
    }

    /**
     * Создать героя данной расы.
     * @param type тип героя.
     * @param squadName название отряда.
     * @return герой данной расы.
     */
    @Override
    public Hero createHero(HeroType type, String squadName) {
        return this.factories.get(type).hero(squadName, this.raceName);
    }

    @Override
    public String toString() {
        return this.raceName;
    }
}
