package ru.job4j;

public class StopGameSimple implements StopGame {
    private boolean stopped = false;

    @Override
    public void stopGame() {
        this.stopped = true;
    }

    @Override
    public boolean gameIsStopped() {
        return this.stopped;
    }
}
