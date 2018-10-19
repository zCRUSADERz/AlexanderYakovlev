package ru.job4j.heroes.attack;

import java.util.Objects;

public class AttackStrengthModifierSimple implements AttackStrengthModifier {
    private final double modifier;

    public AttackStrengthModifierSimple(double modifier) {
        this.modifier = modifier;
    }

    @Override
    public int resultDamage(int initialDamage) {
        return 0;
    }

    @Override
    public String toString() {
        return (this.modifier * 100) + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttackStrengthModifierSimple that = (AttackStrengthModifierSimple) o;
        return Double.compare(that.modifier, this.modifier) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(modifier);
    }
}
