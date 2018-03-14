package ru.job4j.threads;

/**
 * Класс-поток, вычисляющий число символов в строке.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.03.2018
 */
public class CountChar implements Runnable {
    private final String line;

    public CountChar(String line) {
        this.line = line;
    }

    @Override
    public void run() {
        int count = 0;
        for (char ignored : line.toCharArray()) {
            if (Thread.interrupted()) {
                System.out.println("Поток прерван извне.");
                return;
            }
            count++;
        }
        System.out.println("Число символов в строке: " + count);
    }
}
