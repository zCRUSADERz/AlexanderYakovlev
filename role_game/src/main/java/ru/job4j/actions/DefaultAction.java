package ru.job4j.actions;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;

public class DefaultAction implements HeroAction {
    private final Logger logger = Logger.getLogger(DefaultAction.class);

    @Override
    public void act(Hero heroActor) {
        this.logger.info(String.format("%s is inactive.", heroActor));
    }

    @Override
    public String toString() {
        return "Default action. The hero is inactive.";
    }
}
