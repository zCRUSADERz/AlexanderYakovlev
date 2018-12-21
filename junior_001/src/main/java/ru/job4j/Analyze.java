package ru.job4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Analyze {

    public Info diff(List<User> previous, List<User> current) {
        final Info info = new Info();
        final Map<Integer, User> previousMap = previous.stream()
                .collect(Collectors.toMap(user -> user.id, user -> user));
        current.forEach(user -> {
            final Optional<User> optPreviousUser
                    = Optional.ofNullable(previousMap.get(user.id));
            if (optPreviousUser.isPresent()) {
                if (!user.name.equals(optPreviousUser.get().name)) {
                    info.changed++;
                }
                previousMap.remove(user.id);
            } else {
                info.added++;
            }
        });
        info.deleted = previousMap.size();
        return info;

    }

    public static class User {
        private int id;
        private String name;

        public User(final int id, final String name) {
            this.id = id;
            this.name = name;
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
            if (id != user.id) {
                return false;
            }
            return name.equals(user.name);
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + name.hashCode();
            return result;
        }
    }

    public static class Info {
        private int added;
        private int changed;
        private int deleted;

        public Info() {
            this(0, 0, 0);
        }

        public Info(final int added, final int changed, final int deleted) {
            this.added = added;
            this.changed = changed;
            this.deleted = deleted;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Info info = (Info) o;
            if (added != info.added) {
                return false;
            }
            if (changed != info.changed) {
                return false;
            }
            return deleted == info.deleted;
        }

        @Override
        public int hashCode() {
            int result = added;
            result = 31 * result + changed;
            result = 31 * result + deleted;
            return result;
        }
    }
}
