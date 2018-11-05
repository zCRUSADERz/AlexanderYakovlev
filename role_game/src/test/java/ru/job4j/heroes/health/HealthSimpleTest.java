package ru.job4j.heroes.health;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObservable;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * HealthSimpleTest..
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 05.11.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class HealthSimpleTest {
    @Mock
    private HeroDiedObservable diedObservable;
    @Mock
    private Hero hero;
    @Mock
    private HealthSimple.HealthLogger logger;

    private HealthSimple health;

    private void initHealth(int startHP, int minHP) {
        this.health = new HealthSimple(
                startHP,
                minHP,
                this.diedObservable,
                this.logger
        );
    }

    @Test
    public void whenDamageLessThanStartHPThenLogDamage() {
        final int startHP = 100;
        this.initHealth(startHP, 0);
        final int damage = 7;
        final int resultHP = startHP - damage;
        this.health.takeDamage(hero, damage);
        verify(this.logger, only())
                .logDamage(hero, startHP, damage, resultHP);
        verify(this.logger, never())
                .logHeroDie(isA(Hero.class), anyInt(), anyInt());
        verify(this.diedObservable, never()).heroDied(isA(Hero.class));
    }

    @Test
    public void whenDamageGreaterOrEqualThanStartHPThenHeroDied() {
        final int startHP = 10;
        final int minHP = 0;
        this.initHealth(startHP, minHP);
        final int damage = 25;
        final int resultHP = startHP - damage;
        this.health.takeDamage(hero, damage);
        verify(this.logger, times(1))
                .logDamage(this.hero, startHP, damage, resultHP);
        verify(this.logger, times(1))
                .logHeroDie(this.hero, minHP, resultHP);
        verify(this.diedObservable, only()).heroDied(this.hero);
    }

    @Test(expected = IllegalStateException.class)
    public void whenHeroIsDeadThenTakeDamageThrowIllegalStateException() {
        this.initHealth(0, 0);
        this.health.takeDamage(this.hero, 0);
    }
}