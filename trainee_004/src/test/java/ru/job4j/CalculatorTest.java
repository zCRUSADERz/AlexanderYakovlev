package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void whenAdd2Until5() {
        Calculator calc = new Calculator();
        List<Double> buffer = new ArrayList<>();
        calc.multiple(
                1, 5, 2,
                MathUtil::add,
                buffer::add
        );
        assertThat(buffer, is(Arrays.asList(3D, 4D, 5D, 6D)));
    }

    @Test
    public void whenDiv2Until6() {
        Calculator calc = new Calculator();
        List<Double> buffer = new ArrayList<>();
        calc.multiple(
                2, 6, 6,
                MathUtil::div,
                buffer::add
        );
        assertThat(buffer, is(Arrays.asList(3D, 2D, 1D, 1D)));
    }

    @Test
    public void whenLinearFunction() {
        Calculator calc = new Calculator();
        List<Double> buffer = calc.diapason(
                0,
                3,
                MathUtil::linearFunc
        );
        assertThat(buffer, is(Arrays.asList(0D, 2D, 4D)));
    }

    @Test
    public void whenQuadraticFunction() {
        Calculator calc = new Calculator();
        List<Double> buffer = calc.diapason(
                -2,
                2,
                MathUtil::quadraticFunc
        );
        assertThat(buffer, is(Arrays.asList(4D, 1D, 0D, 1D)));
    }

    @Test
    public void whenDecimalLogarithm() {
        Calculator calc = new Calculator();
        List<Double> result = calc.diapason(
                1,
                4,
                Math::log10
        );
        assertThat(result.get(0), closeTo(0D, 0.01D));
        assertThat(result.get(1), closeTo(0.30D, 0.01D));
        assertThat(result.get(2), closeTo(0.47D, 0.01D));
        assertThat(result, iterableWithSize(3));
    }
}