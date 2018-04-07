package ru.job4j;

import org.junit.Test;

import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Ограниченная блокирующая очередь.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 7.04.2018
 */
public class SimpleBlockingQueueTest {

    @Test
    public void testBlockingQueueWithTwoThread() throws InterruptedException {
        LinkedList<Integer> queue = new LinkedList<>();
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(queue, 2);
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    assertThat(blockingQueue.poll(), is(i));
                } catch (InterruptedException e) {
                    throw new IllegalStateException("Поток не должен был быть прерван.");
                }
            }
        });
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    blockingQueue.offer(i);
                } catch (InterruptedException e) {
                    throw new IllegalStateException("Поток не должен был быть прерван.");
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.isEmpty(), is(true));
    }
}