package ru.job4j.bank.exceptions;

/**
 * User not found exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.01.2017
 * @version 1.0
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
