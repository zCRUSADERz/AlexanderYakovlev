package ru.job4j.heroes;

import ru.job4j.actions.actiontarget.RandomTarget;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HeroHealth;
import ru.job4j.observers.HeroDiedObserver;
import ru.job4j.observers.Observable;

import java.util.Objects;

public class HeroInMemory {
    private Hero hero;
    private HeroHealth health;
    private int healthPoints;
    private AttackStrengthModifiers modifiers;
    private RandomTarget target;
    private Observable<HeroDiedObserver> diedObservable;

    public HeroInMemory(Hero hero,
                        HeroHealth health,
                        int healthPoints,
                        AttackStrengthModifiers modifiers,
                        RandomTarget target,
                        Observable<HeroDiedObserver> diedObservable) {
        this.hero = hero;
        this.health = health;
        this.healthPoints = healthPoints;
        this.modifiers = modifiers;
        this.target = target;
        this.diedObservable = diedObservable;
    }

    public Hero getHero() {
        return this.hero;
    }

    public HeroHealth getHealth() {
        return this.health;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public AttackStrengthModifiers getModifiers() {
        return this.modifiers;
    }

    public RandomTarget getTarget() {
        return this.target;
    }

    public void setTarget(RandomTarget target) {
        this.target = target;
    }

    public Observable<HeroDiedObserver> getDiedObservable() {
        return diedObservable;
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }
}
