package ru.job4j.switcher;

import java.util.concurrent.Semaphore;

/**
 * Attach numbers.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.03.2019
 */
public class AttachNumbers implements Runnable {
    private final int number;
    private final NumberString numberString;
    private final Semaphore own;
    private final Semaphore next;

    public AttachNumbers(final int number, final NumberString numberString,
                         final Semaphore own, final Semaphore next) {
        this.number = number;
        this.numberString = numberString;
        this.own = own;
        this.next = next;
    }

    /**
     * Attaches ten characters per semaphore resolution.
     * After attaching characters, release permission to the next semaphore.
     */
    @Override
    public final void run() {
        while (!Thread.interrupted()) {
            try {
                this.own.acquire();
            } catch (final InterruptedException ex) {
                break;
            }
            for (int i = 0; i < 10; i++) {
                this.numberString.attach(this.number);
            }
            this.next.release();
        }
    }
}
