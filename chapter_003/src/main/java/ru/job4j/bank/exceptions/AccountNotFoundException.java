package ru.job4j.bank.exceptions;

/**
 * Account not found exception.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.01.2017
 * @version 1.0
 */
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
