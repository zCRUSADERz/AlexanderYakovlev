package ru.job4j.bank;

import ru.job4j.bank.exceptions.AccountNotFoundException;
import ru.job4j.bank.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        users.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Delete user.
     * @param user - user.
     */
    public void deleteUser(User user) {
        users.remove(user);
    }

    /**
     * Add account to user.
     * @param passport - user passport.
     * @param account - new account.
     * @throws UserNotFoundException - if the user not found in the users.
     */
    public void addAccountToUser(String passport, Account account) throws UserNotFoundException {
        List<Account> accounts = getUserAccounts(passport);
        accounts.add(account);
    }

    /**
     * Delete account from user.
     * @param passport - user passport.
     * @param account - account.
     * @throws UserNotFoundException - if the user not found in the users.
     */
    public void deleteAccountFromUser(String passport, Account account) throws UserNotFoundException {
        List<Account> accounts = getUserAccounts(passport);
        accounts.remove(account);
    }

    /**
     * Get list of user accounts.
     * @param passport - user passport.
     * @return - list of user Accounts
     * @throws UserNotFoundException - if the user not found in the users.
     */
    public List<Account> getUserAccounts(String passport) throws UserNotFoundException {
        List<Account> result = null;
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                result = users.get(user);
                break;
            }
        }
        if (result == null) {
            throw new UserNotFoundException("User with passport: " + passport + " not found.");
        }
        return result;
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
            List<Account> srcAccounts = getUserAccounts(srcPassport);
            List<Account> destAccounts;
            if (srcPassport.equals(destPassport)) {
                destAccounts = srcAccounts;
            } else {
                destAccounts = getUserAccounts(destPassport);
            }
            Account srcAccount = getAccount(srcAccounts, srcRequisite);
            Account destAccount = getAccount(destAccounts, destRequisite);
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
        Account result = null;
        for (Account account : accounts) {
            if (account.getRequisites().equals(requisite)) {
                result = account;
                break;
            }
        }
        if (result == null) {
            throw new AccountNotFoundException("User with requisites: " + requisite + " not found.");
        }
        return result;
    }
}
