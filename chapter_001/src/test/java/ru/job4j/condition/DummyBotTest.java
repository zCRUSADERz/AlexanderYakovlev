package ru.job4j.condition;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Dummy bot test.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 30.12.2017
 * @version 1.0
 */
public class DummyBotTest {

    /**
     * Test greet.
     */
    @Test
    public void whenGreetBot() {
        DummyBot bot = new DummyBot();
        assertThat(bot.answer("Привет, Бот."), is("Привет, умник."));
    }

    /**
     * Test buy.
     */
    @Test
    public void whenBuyBot() {
        DummyBot bot = new DummyBot();
        assertThat(bot.answer("Пока."), is("До скорой встречи."));
    }

    /**
     * Test unknown question.
     */
    @Test
    public void whenUnknownBot() {
        DummyBot bot = new DummyBot();
        assertThat(
                bot.answer("Где ключи от танка?"),
                is("Это ставит меня в тупик. Спросите другой вопрос.")
        );
    }
}
