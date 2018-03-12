package ru.job4j;

import org.junit.Test;

public class SimpleMultiThreadingTest {

    @Test
    public void test() {
        SimpleMultiThreading simple = new SimpleMultiThreading();
        simple.calc("Необходимо создать два Thread. Первый будет считать"
                + "количество слов. Второй количество пробелом в тексте");
    }
}