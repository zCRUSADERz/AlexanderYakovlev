package ru.job4j.bank;

import org.junit.Test;
import ru.job4j.bank.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Bank test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.01.2017
 * @version 1.0
 */
public class BankTest {

    @Test
    public void whenAddNewUser() throws UserNotFoundException {
        Bank bank = new Bank();
        User user = new User("Alexander", "500 123456");
        bank.addUser(user);
        List<Account> result = bank.getUserAccounts(user.getPassport());
        List<Account> expected = new ArrayList<>();
        assertThat(result, is(expected));
    }

    @Test (expected = UserNotFoundException.class)
    public void whenDeleteUserThenGetUserReturnUserNotFoundException()
            throws UserNotFoundException {
        Bank bank = new Bank();
        User user = new User("Alexander", "500 123456");
        bank.addUser(user);
        bank.deleteUser(user);
        bank.getUserAccounts(user.getPassport());
    }

    @Test
    public void whenAddAccountToUserThenGetUserAccountsReturnThisAccount()
            throws UserNotFoundException {
        Bank bank = new Bank();
        User user = new User("Alexander", "500 123456");
        Account userAccount = new Account(0, "1, Alexander, 500 123456");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), userAccount);
        List<Account> result = bank.getUserAccounts(user.getPassport());
        List<Account> expected = new ArrayList<>();
        expected.add(userAccount);
        assertThat(result, is(expected));
    }

    @Test
    public void whenDeleteAccountFromUser() throws UserNotFoundException {
        Bank bank = new Bank();
        User user = new User("Alexander", "500 123456");
        Account userAccount = new Account(0, "1, Alexander, 500 123456");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), userAccount);
        bank.deleteAccountFromUser(user.getPassport(), userAccount);
        List<Account> result = bank.getUserAccounts(user.getPassport());
        List<Account> expected = new ArrayList<>();
        assertThat(result, is(expected));
    }

    @Test
    public void whenTransferMoney()
            throws UserNotFoundException {
        Bank bank = new Bank();
        User user = new User("Alexander", "500 123456");
        User user2 = new User("Sergey", "501 123456");
        Account userAccount = new Account(10, "1, Alexander, 500 123456");
        Account user2Account = new Account(0, "2, Sergey, 501 123456");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), userAccount);
        bank.addUser(user2);
        bank.addAccountToUser(user2.getPassport(), user2Account);
        boolean result = bank.transferMoney(
                user.getPassport(), userAccount.getRequisites(),
                user2.getPassport(), user2Account.getRequisites(),
                5
        );
        assertThat(result, is(true));
    }

    @Test
    public void whenTransferMoneyFromNonExistentAccountsThenReturnFalse()
            throws UserNotFoundException {
        Bank bank = new Bank();
        User user = new User("Alexander", "500 123456");
        User user2 = new User("Sergey", "501 123456");
        Account userAccount = new Account(10, "1, Alexander, 500 123456");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), userAccount);
        bank.addUser(user2);
        boolean result = bank.transferMoney(
                user.getPassport(), "",
                user2.getPassport(), "",
                5
        );
        assertThat(result, is(false));
    }

    @Test
    public void whenTransferMoneyBetweenAccountsOfTheSameUserThenSecondAccountHaveFiveValue()
            throws UserNotFoundException {
        Bank bank = new Bank();
        User user = new User("Alexander", "500 123456");
        Account firstAccount = new Account(10, "1, Alexander, 500 123456");
        Account secondAccount = new Account(0, "2, Alexander, 500 123456");
        bank.addUser(user);
        bank.addAccountToUser(user.getPassport(), firstAccount);
        bank.addAccountToUser(user.getPassport(), secondAccount);
        bank.transferMoney(
                user.getPassport(), firstAccount.getRequisites(),
                user.getPassport(), secondAccount.getRequisites(),
                5
        );
        double result = secondAccount.getValue();
        double expected = 5;
        assertThat(result, is(expected));
    }
}