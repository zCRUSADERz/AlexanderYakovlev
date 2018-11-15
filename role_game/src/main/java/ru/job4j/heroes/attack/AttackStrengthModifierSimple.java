package ru.job4j.heroes.attack;

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
    public int damage(int initialDamage) {
        return (int) (this.modifier * initialDamage);
    }

    @Override
    public String toString() {
        return (int) (this.modifier * 100) + "%";
    }
}
