package ru.job4j.squad;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.newhero.HeroCreatedObservable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SquadsSimple implements Squads {
    private final Set<SquadHeroes> squads;
    private final Map<Hero, SquadHeroes> ownSquads;
    private final Map<Hero, SquadHeroes> enemySquads;
    private final HeroCreatedObservable heroCreatedObservable;

    public SquadsSimple(Set<SquadHeroes> squads,
                        Map<Hero, SquadHeroes> ownSquads,
                        Map<Hero, SquadHeroes> enemySquads,
                        HeroCreatedObservable heroCreatedObservable) {
        this.squads = squads;
        this.ownSquads = ownSquads;
        this.enemySquads = enemySquads;
        this.heroCreatedObservable = heroCreatedObservable;
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
    public void newHeroCreated(Hero hero, SquadHeroes ownSquad, SquadHeroes enemySquad) {
        this.ownSquads.put(hero, ownSquad);
        this.enemySquads.put(hero, enemySquad);
        this.squads.add(ownSquad);
        this.squads.add(enemySquad);
        ownSquad.heroCreated(hero);
        this.heroCreatedObservable.heroCreated(hero);
    }

    @Override
    public void heroDied(Hero hero) {
        this.ownSquads.get(hero).heroDied(hero);
        this.ownSquads.remove(hero);
        this.enemySquads.remove(hero);
    }

    @Override
    public void heroMoved(Hero hero) {
        this.ownSquads.get(hero).heroMoved(hero);
    }
}
