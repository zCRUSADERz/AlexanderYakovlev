package ru.job4j.heroes.attack;

/**
 * Attack strength modifier.
 * Модификатор силы урона.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public interface AttackStrengthModifier {

    /**
     * Возвращает урон с учетом своего модификатора.
     * @param initialDamage изначальный урон.
     * @return урон с учетом модификатора.
     */
    int resultDamage(int initialDamage);
}
