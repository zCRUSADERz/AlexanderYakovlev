package ru.job4j.threads;

/**
 * Простое многопоточное приложение.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.03.2018
 */
public class SimpleMultiThreading {

    public void calc(String line) {
        System.out.println("Calc run");
        Thread t1 = new Thread(() -> {
            int counter = 0;
            for (String ignored : line.split(" ")) {
                counter++;
            }
            System.out.println(String.format("Number of words: %d", counter));
        });
        Thread t2 = new Thread(() -> {
            int counter = 0;
            for (char ch : line.toCharArray()) {
                if (ch == ' ') {
                    counter++;
                }
            }
            System.out.println(String.format("Number of spaces: %d", counter));
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Calc finished.");
    }
}
