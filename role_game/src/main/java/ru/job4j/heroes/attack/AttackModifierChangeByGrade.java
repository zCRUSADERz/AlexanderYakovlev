package ru.job4j.heroes.attack;

import ru.job4j.heroes.Hero;
import ru.job4j.observable.gradechange.GradeChangeObserver;

public class AttackModifierChangeByGrade implements GradeChangeObserver {
    private final AttackStrengthModifiers modifiers;
    private final AttackStrengthModifier modifierByUpgrade;

    public AttackModifierChangeByGrade(AttackStrengthModifiers modifiers,
                                       AttackStrengthModifier modifierByUpgrade) {
        this.modifiers = modifiers;
        this.modifierByUpgrade = modifierByUpgrade;
    }


    @Override
    public void upgraded(Hero hero) {
        this.modifiers.add(hero, this.modifierByUpgrade);
    }

    @Override
    public void degraded(Hero hero) {
        this.modifiers.remove(hero, this.modifierByUpgrade);
    }
}
