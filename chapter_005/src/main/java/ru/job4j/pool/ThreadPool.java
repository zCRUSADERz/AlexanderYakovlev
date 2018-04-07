package ru.job4j.pool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.SimpleBlockingQueue;

/**
 * Пул потоков.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.04.2018
 */
@ThreadSafe
public class ThreadPool {
    private final WorkThread[] pool;
    private final SimpleBlockingQueue<Work> queue;
    @GuardedBy("this")
    private volatile boolean init = false;

    public ThreadPool() {
        this(
                new WorkThread[Runtime.getRuntime().availableProcessors()],
                new SimpleBlockingQueue<>(Runtime.getRuntime().availableProcessors())
        );

    }

    public ThreadPool(WorkThread[] pool, SimpleBlockingQueue<Work> queue) {
        this.pool = pool;
        this.queue = queue;
    }

    /**
     * Добавить работу в пул потоков.
     * @param work - работа.
     * @throws InterruptedException - если поток был прерван.
     */
    public void add(Work work) throws InterruptedException {
        if (!init) {
            initialize();
        }
        queue.offer(work);
    }

    /**
     * Остановить выполнение всех потоков в пуле.
     */
    public void shutdown() {
        for (WorkThread work : pool) {
            work.interrupt();
        }
    }

    private synchronized void initialize() {
        if (!init) {
            for (WorkThread work : pool) {
                work = new WorkThread(queue);
                work.start();
            }
        }
    }
}
