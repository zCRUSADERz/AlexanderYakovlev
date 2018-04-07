package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.SimpleBlockingQueue;

/**
 * Поток выполняющий работы.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.04.2018
 */
@ThreadSafe
public class WorkThread extends Thread {
    private final SimpleBlockingQueue<Work> queue;

    public WorkThread(SimpleBlockingQueue<Work> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                queue.poll().work();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
