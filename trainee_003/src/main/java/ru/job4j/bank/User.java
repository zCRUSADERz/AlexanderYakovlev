package ru.job4j.bank;

/**
 * User.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.01.2017
 * @version 1.0
 */
public class User {
    private String name;
    private String passport;

    User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    public String getPassport() {
        return passport;
    }

    /**
     * @param obj - another user.
     * @return - true if another user have equals passport.
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            if (passport.equals(((User) obj).passport)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return passport.hashCode();
    }
}
