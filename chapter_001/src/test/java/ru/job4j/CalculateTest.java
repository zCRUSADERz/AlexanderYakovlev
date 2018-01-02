package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * CalculateTest.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 29.12.2017
 * @version 1.0
 */
public class CalculateTest {
	
	/**
	 * Test echo.
	 */
	@Test
	public void whenTakeNameThenTreeEchoPlusName() {
		String input = "Alexander Yakovlev";
		String expect = "Echo, echo, echo :Alexander Yakovlev";
		Calculate calc = new Calculate();
		String result = calc.echo(input);
		assertThat(result, is(expect));
	}
}