package ru.job4j.heroes.attack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.job4j.heroes.Hero;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.only;

/**
 * AttackModifierChangeByGradeTest.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 6.11.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class AttackModifierChangeByGradeTest {
    @Mock
    private AttackStrengthModifiers modifiers;
    @Mock
    private AttackStrengthModifier modifierByGrade;
    @InjectMocks
    private AttackModifierChangeByGrade modifierChangeByGrade;
    @Mock
    private Hero hero;

    @Test
    public void whenHeroUpgradedThenHeroTakesIncreasedAttackModifier() {
        this.modifierChangeByGrade.upgraded(this.hero);
        verify(this.modifiers, only())
                .add(this.hero, this.modifierByGrade);
    }

    @Test
    public void whenHeroDegradedThenHeroLoseIncreasedAttackModifier() {
        this.modifierChangeByGrade.degraded(this.hero);
        verify(this.modifiers, only())
                .remove(this.hero, this.modifierByGrade);
    }
}