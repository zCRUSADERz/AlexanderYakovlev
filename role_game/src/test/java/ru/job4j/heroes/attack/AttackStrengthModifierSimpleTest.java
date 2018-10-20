package ru.job4j.heroes.attack;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AttackStrengthModifierSimpleTest {

    @Test
    public void whenInitialDamageEightAndModifierFiftyPercentThenResultDamageReturnFour() {
        final AttackStrengthModifier modifier = new AttackStrengthModifierSimple(0.5d);
        final int expected = 4;
        final int result = modifier.resultDamage(8);
        assertThat(result, is(expected));
    }
}