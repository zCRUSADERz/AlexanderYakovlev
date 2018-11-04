package ru.job4j;

import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.squad.SquadsMapper;
import ru.job4j.utils.RandomElementFromList;

/**
 * GameEnvironment.
 * Содержит основные игровые объекты.
 * Создан только для удобства.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class GameEnvironment {
    private final StopGame stopGame;
    private final SquadsMapper squadsMapper;
    private final HealthHeroes healthHeroes;
    private final AttackStrengthModifiers modifiers;
    private final RandomElementFromList random;

    public GameEnvironment(StopGame stopGame,
                           SquadsMapper squadsMapper,
                           HealthHeroes healthHeroes,
                           AttackStrengthModifiers modifiers,
                           RandomElementFromList random) {
        this.stopGame = stopGame;
        this.squadsMapper = squadsMapper;
        this.healthHeroes = healthHeroes;
        this.modifiers = modifiers;
        this.random = random;
    }

    public StopGame getStopGame() {
        return stopGame;
    }

    public SquadsMapper getSquadsMapper() {
        return squadsMapper;
    }

    public HealthHeroes getHealthHeroes() {
        return healthHeroes;
    }

    public AttackStrengthModifiers getModifiers() {
        return modifiers;
    }

    public RandomElementFromList getRandom() {
        return random;
    }
}
