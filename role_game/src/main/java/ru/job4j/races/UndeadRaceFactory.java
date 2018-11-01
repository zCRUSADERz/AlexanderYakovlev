package ru.job4j.races;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.SendAilment;
import ru.job4j.actions.actiontarget.RandomEnemyTarget;
import ru.job4j.actions.actiontarget.RandomTarget;
import ru.job4j.heroes.HeroFactory;
import ru.job4j.heroes.HeroFactorySimple;
import ru.job4j.heroes.HeroType;
import ru.job4j.heroes.attack.AttackStrengthModifierSimple;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.squad.SquadsMapper;
import ru.job4j.utils.RandomElementFromList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Undead race factory.
 * Фабрика расы нежить.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
//TODO раса должна быть вынесена в конфигурационный файл.
// TODO Много повторяющегося кода!
public class UndeadRaceFactory implements RaceFactory {
    private final SquadsMapper squadsMapper;
    private final HealthHeroes healthHeroes;
    private final AttackStrengthModifiers attackStrengthModifiers;
    private final RandomElementFromList random;

    public UndeadRaceFactory(SquadsMapper squadsMapper, HealthHeroes healthHeroes,
                             AttackStrengthModifiers attackStrengthModifiers,
                             RandomElementFromList random) {
        this.squadsMapper = squadsMapper;
        this.healthHeroes = healthHeroes;
        this.attackStrengthModifiers = attackStrengthModifiers;
        this.random = random;
    }

    /**
     * Вернет расу нежить.
     * @return раса нежить.
     */
    @Override
    public Race createRace() {
        final RandomTarget enemyTarget = new RandomEnemyTarget(this.squadsMapper);
        final Map<HeroType, HeroFactory> factories = new HashMap<>();
        factories.put(HeroType.MAGE, this.mage(enemyTarget));
        factories.put(HeroType.ARCHER, this.archer(enemyTarget));
        factories.put(HeroType.WARRIOR, this.warrior(enemyTarget));
        return new RaceSimple("Нежить", factories);
    }

    //TODO Жестко завязан с HeroType,
    // TODO при малейшем изменении код нужно будет править!
    private HeroFactory mage(RandomTarget enemyTarget) {
        return new HeroFactorySimple(
                "некромант",
                Arrays.asList(
                        new SendAilment(
                                enemyTarget,
                                new AttackStrengthModifierSimple(0.5d),
                                this.attackStrengthModifiers
                        ),
                        new AttackEnemy(
                                "Атаковать ",
                                5,
                                enemyTarget,
                                this.attackStrengthModifiers,
                                this.healthHeroes
                        )
                ), this.random
        );
    }

    private HeroFactory archer(RandomTarget enemyTarget) {
        return new HeroFactorySimple(
                "охотник",
                Arrays.asList(
                        new AttackEnemy(
                                "Стрелять из лука в ",
                                4,
                                enemyTarget,
                                this.attackStrengthModifiers,
                                this.healthHeroes
                        ), new AttackEnemy(
                                "Атаковать ",
                                2,
                                enemyTarget,
                                this.attackStrengthModifiers,
                                this.healthHeroes
                        )
                ), this.random
        );
    }

    //TODO Жестко завязан с HeroType,
    // TODO при малейшем изменении код нужно будет править!
    private HeroFactory warrior(RandomTarget enemyTarget) {
        return new HeroFactorySimple(
                "зомби",
                Collections.singletonList(
                        new AttackEnemy(
                                "Атаковать копьем ",
                                18,
                                enemyTarget,
                                this.attackStrengthModifiers,
                                this.healthHeroes
                        )
                ), this.random
        );
    }
}
