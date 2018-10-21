package ru.job4j.squad;

import ru.job4j.heroes.HeroType;

import java.util.Map;

public interface Opponents {

    void createSquads(Map<HeroType, Integer> numberOfHeroesByType);
}
