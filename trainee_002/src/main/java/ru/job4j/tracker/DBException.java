package ru.job4j.tracker;

public class DBException extends Exception {

    public DBException(String msg, Throwable e) {
        super(msg, e);
    }
}
