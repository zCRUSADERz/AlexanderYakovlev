package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Простая блокировка.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 12.04.2018
 */
@ThreadSafe
public class SimpleLock {
    @GuardedBy("this")
    private volatile Thread owner;
    @GuardedBy("this")
    private volatile boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            this.wait();
        }
        isLocked = true;
        owner = Thread.currentThread();
    }

    public synchronized void unlock() {
        if (owner == Thread.currentThread()) {
            isLocked = false;
        } else {
            throw new IllegalMonitorStateException("Current thread does not hold this lock");
        }
    }
}
