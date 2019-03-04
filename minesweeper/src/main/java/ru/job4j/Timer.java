package ru.job4j;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

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
