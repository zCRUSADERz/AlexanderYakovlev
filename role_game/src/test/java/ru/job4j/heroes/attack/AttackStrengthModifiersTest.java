package ru.job4j.heroes.attack;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.job4j.heroes.Hero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.only;

/**
 * AttackStrengthModifiersTest.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 6.11.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class AttackStrengthModifiersTest {
    @Mock
    private Map<Hero, Collection<AttackStrengthModifier>> modifiersMap;
    @Mock
    private Logger logger;
    @Mock
    private Collection<AttackStrengthModifier> modifierCollection;
    @Mock
    private Hero hero;
    @Mock
    private AttackStrengthModifier modifier;
    @InjectMocks
    private AttackStrengthModifiers modifiers;

    @Test
    public void whenAddModifierThenModifiersMapGetThisModifier() {
        given(this.modifiersMap.containsKey(this.hero)).willReturn(true);
        given(this.modifiersMap.get(this.hero)).willReturn(this.modifierCollection);
        this.modifiers.add(this.hero, this.modifier);
        verify(this.modifierCollection, only()).add(this.modifier);
    }

    @Test
    public void whenRemoveModifierThenThisModifierRemovedFromModifiersMap() {
        given(this.modifiersMap.containsKey(this.hero)).willReturn(true);
        given(this.modifiersMap.get(this.hero)).willReturn(this.modifierCollection);
        given(this.modifierCollection.remove(this.modifier)).willReturn(true);
        this.modifiers.remove(this.hero, this.modifier);
        verify(this.modifierCollection, only()).remove(this.modifier);
    }

    @Test
    public void whenApplyModifiersForHeroWithoutModifiersThenDamageNotChange() {
        given(this.modifiersMap.containsKey(this.hero)).willReturn(true);
        given(this.modifiersMap.get(this.hero)).willReturn(new ArrayList<>());
        final int damage = 10;
        final int result = this.modifiers.applyModifiersFor(this.hero, damage);
        assertThat(result, is(damage));
    }

    @Test
    public void whenApplyModifiersForHeroThenReturnModifiedDamage() {
        final AttackStrengthModifier secondModifier
                = mock(AttackStrengthModifier.class);
        given(this.modifiersMap.containsKey(this.hero)).willReturn(true);
        given(this.modifiersMap.get(this.hero)).willReturn(
                Arrays.asList(this.modifier, secondModifier)
        );
        final int initialDamage = 10;
        final int firstResult = 15;
        final int expectedDamage = 7;
        given(this.modifier.damage(initialDamage)).willReturn(firstResult);
        given(secondModifier.damage(firstResult)).willReturn(expectedDamage);
        final int resultDamage = this.modifiers
                .applyModifiersFor(this.hero, initialDamage);
        assertThat(resultDamage, is(expectedDamage));
    }

    @Test
    public void whenHeroDiedThenRemoveHeroFromModifiersMap() {
        given(this.modifiersMap.containsKey(this.hero)).willReturn(true);
        this.modifiers.heroDied(this.hero);
        verify(this.modifiersMap, times(1)).remove(this.hero);
    }

    @Test
    public void whenHeroMovedThenRemoveAllHeroModifiers() {
        given(this.modifiersMap.containsKey(this.hero)).willReturn(true);
        given(this.modifiersMap.get(this.hero)).willReturn(this.modifierCollection);
        this.modifiers.heroMoved(this.hero);
        verify(this.modifierCollection, only()).clear();
    }

    @Test
    public void whenHeroCreatedThenModifiersMapGetNewEntry() {
        given(this.modifiersMap.containsKey(this.hero)).willReturn(false);
        this.modifiers.heroCreated(this.hero);
        verify(this.modifiersMap, times(1))
                .put(eq(this.hero), anyCollectionOf(AttackStrengthModifier.class));
    }

    @Test(expected = IllegalStateException.class)
    public void whenAddForHeroWithoutHisModifiersThenThrowIllegalStateException() {
        this.modifiers.add(this.hero, this.modifier);
    }

    @Test(expected = IllegalStateException.class)
    public void whenRemoveForHeroWithoutHisModifiersThenThrowIllegalStateException() {
        this.modifiers.remove(this.hero, this.modifier);
    }

    @Test(expected = IllegalStateException.class)
    public void whenApplyModifiersForHeroWithoutHisModifiersThenThrowIllegalStateException() {
        this.modifiers.applyModifiersFor(this.hero, anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void whenHeroWithoutHisModifiersMovedThenThrowIllegalStateException() {
        this.modifiers.heroMoved(this.hero);
    }

    @Test(expected = IllegalStateException.class)
    public void whenHeroWithoutHisModifiersDiedThenThrowIllegalStateException() {
        this.modifiers.heroDied(this.hero);
    }

    @Test(expected = IllegalStateException.class)
    public void whenHeroWithExistModifiersCreatedThenThrowIllegalStateException() {
        given(this.modifiersMap.containsKey(this.hero)).willReturn(true);
        this.modifiers.heroCreated(this.hero);
    }
}