package ru.job4j.squad;

import ru.job4j.heroes.Hero;

import java.util.List;
import java.util.Optional;

public interface SquadHeroes {

    Optional<Hero> hero();

    List<Hero> heroes();
}
