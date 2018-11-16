package ru.job4j.calculator;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Calculator test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.12.2017
 * @version 1.0
 */
public class CalculatorTest {

    /**
     * Test add.
     */
    @Test
    public void whenAddOnePlusTwoThenResultThree() {
        Calculator calc = new Calculator();
        calc.add(1, 2);
        double result = calc.getResult();
        double expected = 3.0;
        assertThat(result, is(expected));
    }

    /**
     * Test subtract.
     */
    @Test
    public void whenSubtractTwoMinusOneThenResultOne() {
        Calculator calc = new Calculator();
        calc.subtract(2, 1);
        double result = calc.getResult();
        double expected = 1.0;
        assertThat(result, is(expected));
    }

    /**
     * Test div.
     */
    @Test
    public void whenDivFourDivideTwoThenResultTwo() {
        Calculator calc = new Calculator();
        calc.div(4, 2);
        double result = calc.getResult();
        double expected = 2.0;
        assertThat(result, is(expected));
    }

    /**
     * Test multiple.
     */
    @Test
    public void whenMultipleTwoMultiplyTwoThenResultFour() {
        Calculator calc = new Calculator();
        calc.multiple(2, 2);
        double result = calc.getResult();
        double expected = 4.0;
        assertThat(result, is(expected));
    }
}
