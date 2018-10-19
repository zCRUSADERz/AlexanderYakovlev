package ru.job4j.heroes;

import java.util.List;

public interface Race {

    List<Hero> squadHeroes(int magicians, int archers, int warriors, String squadName);
}
