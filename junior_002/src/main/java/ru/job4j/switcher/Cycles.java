package ru.job4j.switcher;

import java.util.Collection;
import java.util.concurrent.Semaphore;

/**
 * Several cycles of switching semaphores.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.03.2019
 */
public class Cycles implements Runnable {
    private final int cycles;
    private final Collection<Thread> threads;
    private final Semaphore own;
    private final Semaphore next;

    public Cycles(final int cycles, final Collection<Thread> threads,
                  final Semaphore own, final Semaphore next) {
        this.cycles = cycles;
        this.threads = threads;
        this.own = own;
        this.next = next;
    }

    /**
     * Switches semaphores in order of several cycles.
     */
    @Override
    public final void run() {
        this.threads.forEach(Thread::start);
        try {
            for (int i = 0; i < this.cycles; i++) {
                this.own.acquire();
                this.next.release();
            }
            this.own.acquire();
        } catch (final InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
        this.threads.forEach(Thread::interrupt);
    }
}
