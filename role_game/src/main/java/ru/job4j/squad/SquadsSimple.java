package ru.job4j.squad;

import ru.job4j.heroes.Hero;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SquadsSimple implements Squads {
    private final Set<SquadHeroes> squads;
    private final Map<Hero, SquadHeroes> ownSquads;
    private final Map<Hero, SquadHeroes> enemySquads;

    public SquadsSimple(Set<SquadHeroes> squads,
                        Map<Hero, SquadHeroes> ownSquads,
                        Map<Hero, SquadHeroes> enemySquads) {
        this.squads = squads;
        this.ownSquads = ownSquads;
        this.enemySquads = enemySquads;
    }

    @Override
    public Collection<SquadHeroes> allSquads() {
        return this.squads;
    }

    @Override
    public SquadHeroes enemySquadFor(Hero hero) {
        return this.enemySquads.get(hero);
    }

    @Override
    public SquadHeroes ownSquadFor(Hero hero) {
        return this.ownSquads.get(hero);
    }

    @Override
    public void heroDied(Hero hero) {
        this.ownSquads.remove(hero);
        this.enemySquads.remove(hero);
    }
}
