package ru.job4j.races;

import ru.job4j.actions.AttackEnemy;
import ru.job4j.actions.SendAilment;
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

    @Override
    public Race createRace() {
        final Map<HeroType, HeroFactory> factories = new HashMap<>();
        factories.put(HeroType.MAGE, this.mage());
        factories.put(HeroType.ARCHER, this.archer());
        factories.put(HeroType.WARRIOR, this.warrior());
        return new RaceSimple("Нежить", factories);
    }

    private HeroFactory mage() {
        return new HeroFactorySimple(
                "некромант",
                Arrays.asList(
                        new SendAilment(
                                this.squadsMapper,
                                new AttackStrengthModifierSimple(0.5d),
                                this.attackStrengthModifiers
                        ),
                        new AttackEnemy(
                                "Атаковать ", 5,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        )
                ), this.random
        );
    }

    private HeroFactory archer() {
        return new HeroFactorySimple(
                "охотник",
                Arrays.asList(
                        new AttackEnemy(
                                "Стрелять из лука в ", 4,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        ), new AttackEnemy(
                                "Атаковать ", 2,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        )
                ), this.random
        );
    }

    private HeroFactory warrior() {
        return new HeroFactorySimple(
                "зомби",
                Collections.singletonList(
                        new AttackEnemy(
                                "Атаковать копьем ", 18,
                                this.attackStrengthModifiers, this.squadsMapper, this.healthHeroes
                        )
                ), this.random
        );
    }
}
