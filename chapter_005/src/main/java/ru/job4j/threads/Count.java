package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count {
    @GuardedBy("this")
    private volatile int value = 0;

    public synchronized void increment() {
        this.value++;
    }

    public int get() {
        return this.value;
    }

}
