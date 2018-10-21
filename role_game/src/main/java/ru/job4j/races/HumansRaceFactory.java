package ru.job4j.races;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.grade.GradeActionSimple;
import ru.job4j.actions.grade.UpgradeAction;
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

    @Override
    public Race createRace() {
        final Map<HeroType, HeroFactory> factories = new HashMap<>();
        factories.put(HeroType.MAGE, this.mage());
        factories.put(HeroType.ARCHER, this.archer());
        factories.put(HeroType.WARRIOR, this.warrior());
        return new RaceSimple("Люди", factories);
    }

    private HeroFactory mage() {
        final HeroAction mageActionDefault = new AttackEnemy(
                "Атаковать магией ", 4,
                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
        );
        return new HeroFactorySimple(
                "маг",
                Arrays.asList(
                        new GradeActionSimple(
                                this.random,
                                mageActionDefault,
                                new UpgradeAction(this.squadsMapper)
                        ),
                        mageActionDefault
                ), this.random
        );
    }

    private HeroFactory archer() {
        return new HeroFactorySimple(
                "арбалетчик",
                Arrays.asList(
                        new AttackEnemy(
                                "Стрелять из арбалета в ", 5,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        ), new AttackEnemy(
                                "Атаковать ", 3,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        )
                ), this.random
        );
    }

    private HeroFactory warrior() {
        return new HeroFactorySimple(
                "воин",
                Collections.singletonList(
                        new AttackEnemy(
                                "Атаковать мечом ", 18,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        )
                ), this.random
        );
    }
}
