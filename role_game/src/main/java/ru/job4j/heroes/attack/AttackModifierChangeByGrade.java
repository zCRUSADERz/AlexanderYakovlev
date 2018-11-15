package ru.job4j.heroes.attack;

import ru.job4j.heroes.Hero;
import ru.job4j.observers.DegradeObserver;
import ru.job4j.observers.UpgradeObserver;

/**
 * Attack modifier change by grade.
 * При изменении привилегии у какого либо героя будет изменен
 * модификатор урона в зависимости от привилегированности героя.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class AttackModifierChangeByGrade implements UpgradeObserver, DegradeObserver {
    private final AttackStrengthModifiers modifiers;
    private final AttackStrengthModifier modifierByUpgrade;

    public AttackModifierChangeByGrade(AttackStrengthModifiers modifiers,
                                       AttackStrengthModifier modifierByUpgrade) {
        this.modifiers = modifiers;
        this.modifierByUpgrade = modifierByUpgrade;
    }

    /**
     * Герой был улучшен, ему будет назначен повышенный моификатор урона.
     * @param hero герой которого улучшили.
     */
    @Override
    public void upgraded(Hero hero) {
        this.modifiers.add(hero, this.modifierByUpgrade);
    }

    /**
     * С героя было снято улучшение, его повышенный модификатор урона будет отменен.
     * @param hero герой с которого сняли улучшение.
     */
    @Override
    public void degraded(Hero hero) {
        this.modifiers.remove(hero, this.modifierByUpgrade);
    }

    @Override
    public String toString() {
        return "Change grade observer for modifiers";
    }
}
