package ru.job4j.bank;

import ru.job4j.bank.exceptions.AccountNotFoundException;
import ru.job4j.bank.exceptions.UserNotFoundException;

import java.util.*;

/**
 * Bank.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.01.2017
 * @version 1.0
 */
public class Bank {
    private Map<User, List<Account>> users = new HashMap<>();

    /**
     * Add new user.
     * @param user - new user.
     */
    public void addUser(User user) {
        this.users.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Delete user.
     * @param user - user.
     */
    public void deleteUser(User user) {
        this.users.remove(user);
    }

    /**
     * Add account to user.
     * @param passport - user passport.
     * @param account - new account.
     * @throws UserNotFoundException - if the user not found in the users.
     */
    public void addAccountToUser(String passport, Account account) throws UserNotFoundException {
        List<Account> accounts = this.getUserAccounts(passport);
        accounts.add(account);
    }

    /**
     * Delete account from user.
     * @param passport - user passport.
     * @param account - account.
     * @throws UserNotFoundException - if the user not found in the users.
     */
    public void deleteAccountFromUser(String passport, Account account) throws UserNotFoundException {
        List<Account> accounts = this.getUserAccounts(passport);
        accounts.remove(account);
    }

    /**
     * Get list of user accounts.
     * @param passport - user passport.
     * @return - list of user Accounts
     * @throws UserNotFoundException - if the user not found in the users.
     */
    public List<Account> getUserAccounts(String passport) throws UserNotFoundException {
        Optional<User> userOptional = this.users.keySet()
                .stream()
                .filter(user -> user.getPassport().equals(passport))
                .findFirst();
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with passport: " + passport + " not found.");
        }
        return this.users.get(userOptional.get());
    }

    /**
     * Transferring money from one account to another.
     * @param srcPassport - passport who transfers.
     * @param srcRequisite - accounts requisites from which to withdraw.
     * @param destPassport - passport who receives.
     * @param destRequisite - accounts requisites who receives.
     * @param amount - amount of money.
     * @return - true if transfer was successful.
     */
    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String destRequisite,
                                 double amount) {
        boolean result;
        try {
            final List<Account> srcAccounts = this.getUserAccounts(srcPassport);
            final List<Account> destAccounts;
            if (srcPassport.equals(destPassport)) {
                destAccounts = srcAccounts;
            } else {
                destAccounts = this.getUserAccounts(destPassport);
            }
            final Account srcAccount = this.getAccount(srcAccounts, srcRequisite);
            final Account destAccount = this.getAccount(destAccounts, destRequisite);
            result = srcAccount.transfer(destAccount, amount);
        } catch (UserNotFoundException | AccountNotFoundException e) {
            result = false;
        }
        return result;
    }

    /**
     * Get account from the list of accounts.
     * @param accounts - list of accounts.
     * @param requisite - requisites of account;
     * @return - required account.
     * @throws AccountNotFoundException - if the account not found in the accounts.
     */
    private Account getAccount(List<Account> accounts, String requisite) throws AccountNotFoundException {
        Optional<Account> accountOptional = accounts.stream()
                .filter(account -> account.getRequisites().equals(requisite))
                .findFirst();
        if (!accountOptional.isPresent()) {
            throw new AccountNotFoundException("User with requisites: " + requisite + " not found.");
        }
        return accountOptional.get();
    }
}
