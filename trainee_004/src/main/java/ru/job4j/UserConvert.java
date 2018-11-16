package ru.job4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class UserConvert {

    public static class User {
        private final String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("User{name='%s'}", this.name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public List<User> convert(List<String> names, Function<String, User> func) {
        List<User> users = new ArrayList<>();
        names.forEach(
                n -> users.add(func.apply(n))
        );
        return users;
    }
}
