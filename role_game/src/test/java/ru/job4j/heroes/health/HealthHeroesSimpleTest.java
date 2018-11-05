package ru.job4j.heroes.health;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.job4j.heroes.Hero;
import ru.job4j.observable.die.HeroDiedObservable;

import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * HealthHeroesSimpleTest.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 5.11.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class HealthHeroesSimpleTest {
    @Mock
    private Map<Hero, HeroHealth> heroHealthMap;
    @Mock
    private HeroDiedObservable diedObservable;
    @Mock
    private Hero hero;
    @Mock
    private Logger logger;
    @InjectMocks
    private HealthHeroesSimple healthHeroes;

    @Test(expected = IllegalStateException.class)
    public void whenAttackHeroWithoutHisHealthThenThrowIllegalStateException() {
        given(this.heroHealthMap.get(any(Hero.class))).willReturn(null);
        this.healthHeroes.attackHero(this.hero, anyInt());
    }

    @Test
    public void whenAttackHeroThenHisHealthTakeDamage() {
        final HeroHealth health = mock(HeroHealth.class);
        given(this.heroHealthMap.get(this.hero)).willReturn(health);
        final int damage = 10;
        this.healthHeroes.attackHero(this.hero, damage);
        verify(health, only()).takeDamage(this.hero, damage);
    }

    @Test(expected = IllegalStateException.class)
    public void whenHeroWithoutHealthDiedThenThrowIllegalStateException() {
        this.healthHeroes.heroDied(this.hero);
    }

    @Test
    public void whenHeroDiedThenHealthThisHeroRemoved() {
        given(this.heroHealthMap.containsKey(this.hero)).willReturn(true);
        this.healthHeroes.heroDied(this.hero);
        verify(this.heroHealthMap, times(1))
                .remove(this.hero);
    }

    @Test(expected = IllegalStateException.class)
    public void whenHeroWithExistingHealthCreatedThenThrowIllegalStateException() {
        given(this.heroHealthMap.containsKey(this.hero)).willReturn(true);
        this.healthHeroes.heroCreated(this.hero);
    }

    @Test
    public void whenHeroCreatedThenCreateNewHealthForHero() {
        given(this.heroHealthMap.containsKey(this.hero)).willReturn(false);
        this.healthHeroes.heroCreated(this.hero);
        verify(this.heroHealthMap, times(1))
                .put(eq(this.hero), any(HealthSimple.class));
    }
}