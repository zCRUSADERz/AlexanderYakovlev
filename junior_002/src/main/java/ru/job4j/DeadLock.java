package ru.job4j;

import java.util.concurrent.CountDownLatch;

/**
 * Example of DeadLock.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.03.2019
 */
public class DeadLock {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final SomeObject firstObject = new SomeObject(
                countDownLatch, "firstObject"
        );
        final SomeObject secondObject = new SomeObject(
                countDownLatch, "secondObject"
        );
        final Thread firstThread = new Thread(
                () -> firstObject.someMethod(secondObject)
        );
        final Thread secondThread = new Thread(
                () -> secondObject.someMethod(firstObject)
        );
        System.out.println(firstThread.getState());
        System.out.println(secondThread.getState());
        firstThread.start();
        secondThread.start();
        for (int i = 0; i < 5; i++) {
            System.out.println(firstThread.getState());
            System.out.println(secondThread.getState());
            Thread.sleep(500);
        }
        System.exit(1);
    }


    public static class SomeObject {
        private final CountDownLatch countDownLatch;
        private final String name;

        public SomeObject(final CountDownLatch countDownLatch, final String name) {
            this.countDownLatch = countDownLatch;
            this.name = name;
        }

        public synchronized final void someMethod(final SomeObject other) {
            try {
                System.out.println(
                        String.format(
                                "Thread: %s, enter in synchronized method. Object: %s.",
                                Thread.currentThread().getName(),
                                this.toString()
                        )
                );
                this.countDownLatch.countDown();
                this.countDownLatch.await();
                other.someMethod();
            } catch (final InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        }

        public synchronized final void someMethod() {

        }

        @Override
        public String toString() {
            return "SomeObject{" + "name='" + this.name + '\'' + '}';
        }
    }
}
