package ru.job4j.actions;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;


/**
 * Inaction.
 * Бездейсвие.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class Inaction implements HeroAction {
    private final Logger logger = Logger.getLogger(Inaction.class);

    /**
     * Бездействовать.
     * @param heroActor герой выполняющий действие.
     */
    @Override
    public void act(Hero heroActor) {
        this.logger.info(String.format("%s is inactive.", heroActor));
    }

    @Override
    public String toString() {
        return "Hero is inactive.";
    }
}
