package ru.job4j.heroes;

import ru.job4j.actions.HeroAction;
import ru.job4j.utils.RandomElementFromList;

import java.util.List;

public class HeroFactorySimple implements HeroFactory {
    private final String desc;
    private final List<HeroAction> actions;
    private final RandomElementFromList random;
    private int counter = 1;

    public HeroFactorySimple(String desc, List<HeroAction> actions,
                             RandomElementFromList random) {
        this.desc = desc;
        this.actions = actions;
        this.random = random;
    }

    public Hero hero(String squadName, String raceName) {
        return new HeroSimple(
                String.format(
                        "Hero-%s(%s)[%s]<%s>",
                        this.counter++, this.desc,
                        squadName, raceName
                ), this.actions,
                this.random
        );
    }
}
