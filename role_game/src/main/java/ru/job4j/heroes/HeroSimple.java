package ru.job4j.heroes;

import org.apache.log4j.Logger;
import ru.job4j.actions.HeroAction;
import ru.job4j.utils.RandomElementFromList;

import java.util.List;

/**
 * Hero.
 * герой.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
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

    /**
     * Герой выполнит рандомное действие из списка своих действий.
     */
    @Override
    public void doAction() {
        final HeroAction action = this.random.randomElement(this.actions);
        this.logger.info(
                String.format(
                        "%s, choose action: %s.",
                        this.name, action
                )
        );
        action.act(this);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
