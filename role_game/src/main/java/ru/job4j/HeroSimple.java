package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.actions.HeroAction;
import ru.job4j.heroes.Hero;
import ru.job4j.utils.RandomElementFromList;

import java.util.List;

public class HeroSimple implements Hero {
    private final String name;
    private final List<HeroAction> actions;
    private final RandomElementFromList random;
    private final Logger logger = Logger.getLogger(HeroSimple.class);

    public HeroSimple(String name, List<HeroAction> actions,
                      RandomElementFromList random) {
        this.name = name;
        this.actions = actions;
        this.random = random;
    }

    @Override
    public void doAction() {
        HeroAction action = this.random.randomElement(this.actions);
        this.logger.info(
                String.format(
                        "%s, chose action: %s.",
                        this.name, action
                )
        );
        action.act();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
