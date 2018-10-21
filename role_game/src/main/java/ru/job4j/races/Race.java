package ru.job4j.races;

import ru.job4j.heroes.Hero;
import ru.job4j.heroes.HeroType;

public interface Race {

    Hero createHero(HeroType type, String squadName);
}
