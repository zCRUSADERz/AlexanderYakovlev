package ru.job4j.heroes;

import ru.job4j.HeroSimple;
import ru.job4j.actions.HeroAction;
import ru.job4j.utils.RandomElementFromList;

import java.util.List;

public class HeroFactorySimple implements HeroFactory {
    private final String desc;
    private final List<HeroAction> actions;
    private final RandomElementFromList random;

    public HeroFactorySimple(String desc, List<HeroAction> actions, RandomElementFromList random) {
        this.desc = desc;
        this.actions = actions;
        this.random = random;
    }

    public Hero hero(String name, String squadName) {
        return new HeroSimple(
                String.format("Hero-%s (%s)[%s]", name, this.desc, squadName),
                this.actions,
                random
        );
    }
}
