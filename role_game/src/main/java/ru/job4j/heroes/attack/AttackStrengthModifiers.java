package ru.job4j.heroes.attack;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObserver;
import ru.job4j.observable.move.HeroMovedObserver;
import ru.job4j.observable.newhero.HeroCreatedObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AttackStrengthModifiers implements HeroDiedObserver, HeroMovedObserver, HeroCreatedObserver {
    private final Map<Hero, Collection<AttackStrengthModifier>> modifiersMap;

    public AttackStrengthModifiers() {
        this(new HashMap<>());
    }

    public AttackStrengthModifiers(
            Map<Hero, Collection<AttackStrengthModifier>> modifiersMap) {
        this.modifiersMap = modifiersMap;
    }

    public void add(Hero hero, AttackStrengthModifier modifier) {
        this.modifiersMap.get(hero).add(modifier);
    }

    public void remove(Hero hero, AttackStrengthModifier modifier) {
        this.modifiersMap.get(hero).remove(modifier);
    }

    public Collection<AttackStrengthModifier> modifiersFor(Hero hero) {
        return this.modifiersMap.get(hero);
    }

    @Override
    public void heroDied(Hero hero) {
        this.modifiersMap.remove(hero);
    }

    @Override
    public void heroMoved(Hero hero) {
        this.modifiersMap.get(hero).clear();
    }

    @Override
    public void heroCreated(Hero hero) {
        this.modifiersMap.put(hero, new ArrayList<>());
    }
}
