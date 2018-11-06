package ru.job4j.heroes.attack;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * AttackStrengthModifierSimpleTest.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 21.10.2018
 */
public class AttackStrengthModifierSimpleTest {

    @Test
    public void whenInitialDamageEightAndModifierFiftyPercentThenResultDamageReturnFour() {
        final AttackStrengthModifier modifier = new AttackStrengthModifierSimple(0.5d);
        final int expected = 4;
        final int result = modifier.resultDamage(8);
        assertThat(result, is(expected));
    }

    @Test
    public void whenModifier50PercentThenToStringReturn50Percent() {
        final AttackStrengthModifier modifier = new AttackStrengthModifierSimple(0.5d);
        final String expected = "50%";
        final String result = modifier.toString();
        assertThat(result, is(expected));
    }
}