package ru.job4j.heroes.attack;

import java.util.Objects;

/**
 * Attack strength modifier.
 * Модификатор силы урона.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class AttackStrengthModifierSimple implements AttackStrengthModifier {
    private final double modifier;

    public AttackStrengthModifierSimple(double modifier) {
        this.modifier = modifier;
    }

    /**
     * Возвращает урон с учетом своего модификатора.
     * @param initialDamage изначальный урон.
     * @return урон с учетом модификатора.
     */
    @Override
    public int resultDamage(int initialDamage) {
        return (int) (this.modifier * initialDamage);
    }

    @Override
    public String toString() {
        return (int) (this.modifier * 100) + "%";
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
