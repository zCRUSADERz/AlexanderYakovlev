package ru.job4j.bank;

/**
 * User account in bank.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.01.2017
 * @version 1.0
 */
public class Account {
    /**
     * Amount of money on the account.
     */
    private double value;
    private String requisites;

    Account(double value, String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    public double getValue() {
        return value;
    }

    public String getRequisites() {
        return requisites;
    }

    /**
     * Transfer money to another user account.
     * @param destAccount - transfer destination.
     * @param amount - amount of money.
     * @return - true if transfer was successful.
     */
    public boolean transfer(Account destAccount, double amount) {
        boolean result = false;
        if (value >= amount && amount >= 0) {
            value -= amount;
            destAccount.value += amount;
            result = true;
        }
        return result;
    }

    /**
     * @param obj - another account.
     * @return - true if another account have equals requisites.
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            if (requisites.equals(((Account) obj).requisites)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return requisites.hashCode();
    }
}
