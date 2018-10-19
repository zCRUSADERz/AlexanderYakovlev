package ru.job4j;

import org.apache.log4j.Logger;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.upgrade.HeroUpgradeObservable;
import ru.job4j.utils.RandomElementFromList;

import java.util.*;

public class SquadSimple implements SquadHeroes {
    private final String squadName;
    private final Set<Hero> squad;
    private final Set<Hero> regularHeroes;
    private final Set<Hero> upgradedHeroes = new HashSet<>();
    private final HeroUpgradeObservable observable;
    private final RandomElementFromList random;
    private final Stop stopGame;
    private final Logger logger = Logger.getLogger(SquadSimple.class);

    public SquadSimple(String squadName, Set<Hero> squad, Set<Hero> regularHeroes,
                       HeroUpgradeObservable observable,
                       RandomElementFromList random, Stop stopGame) {
        this.squadName = squadName;
        this.squad = squad;
        this.regularHeroes = regularHeroes;
        this.observable = observable;
        this.random = random;
        this.stopGame = stopGame;
    }

    @Override
    public Hero randomHero() {
        return this.random.randomElement(new ArrayList<>(this.squad));
    }

    @Override
    public Collection<Hero> upgradedHeroes() {
        return Collections.unmodifiableCollection(this.upgradedHeroes);
    }

    @Override
    public Collection<Hero> regularHeroes() {
        return Collections.unmodifiableCollection(this.regularHeroes);
    }

    @Override
    public void upgradeHero(Hero hero) {
        if (!this.squad.contains(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s not in squad - %s.",
                            hero, this.squadName
                    )
            );
        }
        if (this.regularHeroes.remove(hero)) {
            this.upgradedHeroes.add(hero);
            this.observable.upgraded(hero);
            this.logger.info(String.format("%s was upgraded.", hero));
        }
    }

    @Override
    public void degradeHero(Hero hero) {
        if (!this.upgradedHeroes.contains(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s not in squad - %s.",
                            hero, this.squadName
                    )
            );
        }
        if (this.upgradedHeroes.remove(hero)) {
            this.regularHeroes.add(hero);
            this.observable.degraded(hero);
            this.logger.info(String.format("%s was degraded.", hero));
        }
    }

    @Override
    public void heroDied(Hero hero) {
        if (this.squad.remove(hero)) {
            this.logger.info(
                    String.format(
                            "%s, was removed from squad - %s. " +
                                    "Heroes in squad: %s",
                            hero, this.squadName, this.squad
                    )
            );
            this.regularHeroes.remove(hero);
            this.upgradedHeroes.remove(hero);
        }
        if (this.squad.size() == 0) {
            this.logger.info(
                    String.format("Squad - %s, was killed.", this.squadName)
            );
            this.stopGame.stopGame();
        }
    }

    @Override
    public void heroMoved(Hero hero) {
        if (this.upgradedHeroes.remove(hero)) {
            this.regularHeroes.add(hero);
        }
    }
}
