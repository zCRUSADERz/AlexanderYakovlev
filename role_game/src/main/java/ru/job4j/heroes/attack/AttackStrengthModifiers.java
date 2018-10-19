package ru.job4j.heroes.attack;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.DieObserver;
import ru.job4j.observable.move.HeroMovedObserver;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class AttackStrengthModifiers implements DieObserver, HeroMovedObserver {
    private final Map<Hero, Set<AttackStrengthModifier>> modifiersMap;

    public AttackStrengthModifiers(
            Map<Hero, Set<AttackStrengthModifier>> modifiersMap) {
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
}
