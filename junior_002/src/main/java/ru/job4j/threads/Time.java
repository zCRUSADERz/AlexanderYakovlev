package ru.job4j.threads;

/**
 * Останавливает переданный поток через заданный промежуток времени.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 14.03.2018
 */
public class Time implements Runnable {
    private final Thread thread;
    private final long delay;

    public Time(Thread thread, long delay) {
        this.thread = thread;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
