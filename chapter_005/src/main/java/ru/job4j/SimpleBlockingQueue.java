package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Ограниченная блокирующая очередь.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.04.2018
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue;
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this(new LinkedList<>(), limit);

    }

    public SimpleBlockingQueue(Queue<T> queue, int limit) {
        this.queue = queue;
        this.limit = limit;
    }

    /**
     * Положить в очередь.
     * @param value - объект, который будет добавлен в очередь.
     * @throws InterruptedException - true, если прервано извне.
     */
    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= limit) {
                this.wait();
            }
            queue.add(value);
            notifyAll();
        }
    }

    /**
     * Взять из очереди
     * @return - объект, взятый из очереди.
     * @throws InterruptedException - true, если прервано извне.
     */
    public T poll() throws InterruptedException {
        T result;
        synchronized (this) {
            while (queue.isEmpty()) {
                this.wait();
            }
            result = queue.poll();
            notifyAll();
        }
        return result;
    }
}
