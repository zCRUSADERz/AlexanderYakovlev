package ru.job4j;

public class StopSimple implements Stop {
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
