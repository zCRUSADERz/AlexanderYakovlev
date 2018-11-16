package ru.job4j.threads;

import org.junit.Before;
import org.junit.Test;

/**
 * "Тестирование" прерывания потока извне.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.03.2018
 */
public class CountCharTest {
    private String line;

    @Before
    public void setLine() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            result.append(Math.random());
        }
        line = result.toString();
    }

    @Test
    public void test() throws InterruptedException {
        Thread counter = new Thread(new CountChar(line));
        Thread time = new Thread(new Time(counter, 1));
        counter.start();
        time.start();
        counter.join();
        time.join();
    }

}