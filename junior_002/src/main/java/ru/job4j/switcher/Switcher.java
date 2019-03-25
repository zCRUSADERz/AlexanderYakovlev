package ru.job4j.switcher;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 * Switcher.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.03.2019
 */
public class Switcher {

    public static void main(String[] args) throws InterruptedException {
        new Switcher().start();
    }

    public final void start() throws InterruptedException {
        final NumberString numbers = new NumberString();
        final Semaphore first = new Semaphore(1);
        final Semaphore second = new Semaphore(0);
        final Semaphore third = new Semaphore(0);
        final Semaphore fourth = new Semaphore(0);
        final Semaphore last = new Semaphore(0);
        final Thread thread = new Thread(
                new Cycles(
                        3,
                        Arrays.asList(
                                new Thread(
                                        new AttachNumbers(
                                                1, numbers, first, second
                                        )
                                ),
                                new Thread(
                                        new AttachNumbers(
                                                2, numbers, second, third
                                        )
                                ),
                                new Thread(
                                        new AttachNumbers(
                                                3, numbers, third, fourth
                                        )
                                ),
                                new Thread(
                                        new AttachNumbers(
                                                4, numbers, fourth, last
                                        )
                                )
                        ),
                        last,
                        first
                )
        );
        thread.start();
        thread.join();
        System.out.println(numbers.string());
    }
}
