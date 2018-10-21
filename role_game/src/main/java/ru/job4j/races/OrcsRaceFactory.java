package ru.job4j.races;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.DefaultAction;
import ru.job4j.actions.HeroAction;
import ru.job4j.actions.grade.DegradeAction;
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

public class OrcsRaceFactory implements RaceFactory {
    private final SquadsMapper squadsMapper;
    private final HealthHeroes healthHeroes;
    private final AttackStrengthModifiers attackStrengthModifiers;
    private final RandomElementFromList random;

    public OrcsRaceFactory(SquadsMapper squadsMapper, HealthHeroes healthHeroes,
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
        return new RaceSimple("Орки", factories);
    }

    private HeroFactory mage() {
        final HeroAction defaultAction = new DefaultAction();
        return new HeroFactorySimple(
                "шаман",
                Arrays.asList(
                        new GradeActionSimple(
                                this.random,
                                defaultAction,
                                new UpgradeAction(this.squadsMapper)
                        ),
                        new GradeActionSimple(
                                random,
                                defaultAction,
                                new DegradeAction(this.squadsMapper)
                        )
                ), this.random
        );
    }

    private HeroFactory archer() {
        return new HeroFactorySimple(
                "лучник",
                Arrays.asList(
                        new AttackEnemy(
                                "Стрелять из лука в ", 3,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        ), new AttackEnemy(
                                "Удар клинком по ", 2,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        )
                ), this.random
        );
    }

    private HeroFactory warrior() {
        return new HeroFactorySimple(
                "гоблин",
                Collections.singletonList(
                        new AttackEnemy(
                                "Атака дубиной по ", 20,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        )
                ), this.random
        );
    }
}
