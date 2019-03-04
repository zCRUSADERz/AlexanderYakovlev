package ru.job4j;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Timer.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.03.2019
 */
public class Timer implements Runnable {
    private final JTextField timeField;
    private final AtomicLong startedTime;
    private final AtomicBoolean gameFinished;

    public Timer(final JTextField timeField, final AtomicLong startedTime,
                 final AtomicBoolean gameFinished) {
        this.timeField = timeField;
        this.startedTime = startedTime;
        this.gameFinished = gameFinished;
    }

    /**
     * Обновляет текстовое поле каждые 100 миллисекунд. Записывает время
     * прошедшее с начала текущего раунда.
     */
    @Override
    public final void run() {
        while (!Thread.interrupted()) {
            if (!this.gameFinished.get()) {
                final long started = this.startedTime.get();
                final long time = (System.currentTimeMillis() - started) / 1000;
                final long result;
                if (time > 999) {
                    result = 999;
                } else {
                    result = time;
                }
                this.timeField.setText(String.format("%03d", result));
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
