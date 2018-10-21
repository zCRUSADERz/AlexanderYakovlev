package ru.job4j.squad;

import org.apache.log4j.Logger;
import ru.job4j.StopGame;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.gradechange.GradeChangeObservable;
import ru.job4j.utils.RandomElementFromList;

import java.util.*;

public class SquadSimple implements SquadHeroes {
    private final String squadName;
    private final Set<Hero> squad;
    private final Set<Hero> regularHeroes;
    private final Set<Hero> upgradedHeroes;
    private final GradeChangeObservable observable;
    private final RandomElementFromList random;
    private final StopGame stopGame;
    private final Logger logger = Logger.getLogger(SquadSimple.class);

    public SquadSimple(String squadName, GradeChangeObservable observable,
                        RandomElementFromList random, StopGame stopGame) {
        this(
                squadName,
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                observable,
                random,
                stopGame
        );
    }

    public SquadSimple(String squadName, Set<Hero> squad, Set<Hero> regularHeroes,
                       Set<Hero> upgradedHeroes, GradeChangeObservable observable,
                       RandomElementFromList random, StopGame stopGame) {
        this.squadName = squadName;
        this.squad = squad;
        this.regularHeroes = regularHeroes;
        this.upgradedHeroes = upgradedHeroes;
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
        if (!this.regularHeroes.contains(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s not regular hero in squad %s."
                                    + "All heroes: %s, regular heroes: %s",
                            hero, this.squadName, this.squad, this.regularHeroes
                    )
            );
        }
        this.regularHeroes.remove(hero);
        this.upgradedHeroes.add(hero);
        this.observable.upgraded(hero);
        this.logger.info(String.format("%s was upgraded.", hero));
    }

    @Override
    public void degradeHero(Hero hero) {
        if (!this.upgradedHeroes.contains(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s not upgraded hero in squad %s."
                                    + "All heroes: %s, upgraded heroes: %s",
                            hero, this.squadName, this.squad, this.upgradedHeroes
                    )
            );
        }
        this.upgradedHeroes.remove(hero);
        this.regularHeroes.add(hero);
        this.observable.degraded(hero);
        this.logger.info(String.format("%s was degraded.", hero));
    }

    @Override
    public void heroDied(Hero hero) {
        if (!this.squad.contains(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s not in squad %s."
                                    + "All heroes: %s.",
                            hero, this.squadName, this.squad
                    )
            );
        }
        this.squad.remove(hero);
        this.logger.info(
                String.format(
                        "%s, was removed from squad - %s. "
                                + "Heroes in squad: %s",
                        hero, this.squadName, this.squad
                )
        );
        this.regularHeroes.remove(hero);
        this.upgradedHeroes.remove(hero);
        if (this.squad.size() == 0) {
            this.logger.info(
                    String.format("Squad - %s was killed.", this.squadName)
            );
            this.stopGame.stopGame();
        }
    }

    @Override
    public void heroMoved(Hero hero) {
        if (!this.squad.contains(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s not in squad %s."
                                    + "All heroes: %s.",
                            hero, this.squadName, this.squad
                    )
            );
        }
        if (this.upgradedHeroes.remove(hero)) {
            this.regularHeroes.add(hero);
        }
    }

    @Override
    public void heroCreated(Hero hero) {
        if (this.squad.contains(hero)) {
            throw new IllegalStateException(
                    String.format(
                            "%s already in squad %s."
                                    + "All heroes: %s.",
                            hero, this.squadName, this.squad
                    )
            );
        }
        this.squad.add(hero);
        this.regularHeroes.add(hero);
    }

    @Override
    public String toString() {
        return this.squadName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SquadSimple that = (SquadSimple) o;
        return Objects.equals(squadName, that.squadName)
                && Objects.equals(squad, that.squad)
                && Objects.equals(regularHeroes, that.regularHeroes)
                && Objects.equals(upgradedHeroes, that.upgradedHeroes)
                && Objects.equals(observable, that.observable)
                && Objects.equals(random, that.random)
                && Objects.equals(stopGame, that.stopGame)
                && Objects.equals(logger, that.logger);
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                squadName, squad, regularHeroes,
                upgradedHeroes, observable, random,
                stopGame, logger
        );
    }
}
