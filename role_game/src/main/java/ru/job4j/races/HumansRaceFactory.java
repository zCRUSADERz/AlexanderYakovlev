package ru.job4j.races;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.grade.GradeActionSimple;
import ru.job4j.actions.grade.UpgradeAction;
import ru.job4j.actions.target.RandomEnemyTarget;
import ru.job4j.actions.target.RandomTarget;
import ru.job4j.actions.target.RandomTargetForGrade;
import ru.job4j.actions.target.RandomTargetForUpgrade;
import ru.job4j.heroes.HeroFactory;
import ru.job4j.heroes.HeroFactorySimple;
import ru.job4j.heroes.HeroType;
import ru.job4j.heroes.attack.AttackStrengthModifiers;
import ru.job4j.heroes.health.HealthHeroes;
import ru.job4j.squad.SquadsMapper;
import ru.job4j.utils.RandomElementFromList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Humans race factory.
 * Фабрика расы людей.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
//TODO раса должна быть вынесена в конфигурационный файл.
// TODO Много повторяющегося кода!
public class HumansRaceFactory implements RaceFactory {
    private final SquadsMapper squadsMapper;
    private final HealthHeroes healthHeroes;
    private final AttackStrengthModifiers attackStrengthModifiers;
    private final RandomElementFromList random;

    public HumansRaceFactory(SquadsMapper squadsMapper, HealthHeroes healthHeroes,
                             AttackStrengthModifiers attackStrengthModifiers,
                             RandomElementFromList random) {
        this.squadsMapper = squadsMapper;
        this.healthHeroes = healthHeroes;
        this.attackStrengthModifiers = attackStrengthModifiers;
        this.random = random;
    }

    /**
     * Вернет расу людей.
     * @return раса людей.
     */
    @Override
    public Race createRace() {
        final RandomTargetForGrade forUpgrade
                = new RandomTargetForUpgrade(this.squadsMapper);
        final RandomTarget enemyTarget = new RandomEnemyTarget(this.squadsMapper);
        final Map<HeroType, HeroFactory> factories = new HashMap<>();
        factories.put(HeroType.MAGE, this.mage(forUpgrade, enemyTarget));
        factories.put(HeroType.ARCHER, this.archer(enemyTarget));
        factories.put(HeroType.WARRIOR, this.warrior(enemyTarget));
        return new RaceSimple("Люди", factories);
    }

    //TODO Жестко завязан с HeroType,
    // TODO при малейшем изменении код нужно будет править!
    private HeroFactory mage(RandomTargetForGrade forUpgrade,
                             RandomTarget enemyTarget) {
        final HeroAction mageActionDefault = new AttackEnemy(
                "Атаковать магией ",
                4,
                enemyTarget,
                this.attackStrengthModifiers,
                this.healthHeroes
        );
        return new HeroFactorySimple(
                "маг",
                Arrays.asList(
                        new GradeActionSimple(
                                mageActionDefault,
                                new UpgradeAction(this.squadsMapper),
                                forUpgrade
                        ),
                        mageActionDefault
                ), this.random
        );
    }

    //TODO Жестко завязан с HeroType,
    // TODO при малейшем изменении код нужно будет править!
    private HeroFactory archer(RandomTarget enemyTarget) {
        return new HeroFactorySimple(
                "арбалетчик",
                Arrays.asList(
                        new AttackEnemy(
                                "Стрелять из арбалета в ",
                                5,
                                enemyTarget,
                                this.attackStrengthModifiers,
                                this.healthHeroes
                        ), new AttackEnemy(
                                "Атаковать ",
                                3,
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
                "воин",
                Collections.singletonList(
                        new AttackEnemy(
                                "Атаковать мечом ",
                                18,
                                enemyTarget,
                                this.attackStrengthModifiers,
                                this.healthHeroes
                        )
                ), this.random
        );
    }
}
