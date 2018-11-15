package ru.job4j.squad;

import ru.job4j.heroes.Hero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Squads {
    private final Collection<Squad> squads = new ArrayList<>();

    public List<Hero> heroes(SquadSubType type) {
        final List<Hero> result = new ArrayList<>();
        this.squads.forEach(
                squad -> result.addAll(squad.operation(type).heroes())
        );
        return result;
    }

    public void newSquad(Squad squad) {
        this.squads.add(squad);
    }
}
