package ru.job4j.heroes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RaceSimple implements Race {
    private final HeroFactory magiciansFactory;
    private final HeroFactory archersFactory;
    private final HeroFactory warriorsFactory;

    public RaceSimple(HeroFactory magiciansFactory, HeroFactory archersFactory, HeroFactory warriorsFactory) {
        this.magiciansFactory = magiciansFactory;
        this.archersFactory = archersFactory;
        this.warriorsFactory = warriorsFactory;
    }

    @Override
    public List<Hero> squadHeroes(int magicians, int archers,
                                  int warriors, String squadName) {
        final List<Hero> result = new ArrayList<>();
        result.addAll(
                this.heroes(magicians, squadName, this.magiciansFactory)
        );
        result.addAll(
                this.heroes(archers, squadName, this.archersFactory)
        );
        result.addAll(
                this.heroes(warriors, squadName, this.warriorsFactory)
        );
        return result;
    }

    private List<Hero> heroes(int numOfHeroes, String squadName, HeroFactory heroFactory) {
        return Stream.iterate(1, n -> n + 1)
                .limit(numOfHeroes)
                .map(n -> heroFactory.hero(String.valueOf(n), squadName))
                .collect(Collectors.toList());
    }
}
