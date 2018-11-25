package ru.job4j.persistence.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class User {
    private final long id;
    private final String name;
    private final String login;
    private final String email;
    private final LocalDateTime createDate;

    public User(long id,
                String name,
                String login,
                String email,
                LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = createDate;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public User rename(String newName) {
        return new User(
                this.id,
                newName,
                this.login,
                this.email,
                this.createDate
        );
    }

    public boolean loginIsEqual(String login) {
        return this.login.equals(login);
    }

    public String createDateToString() {
        return this.createDate.format(
                DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.SHORT,
                        FormatStyle.SHORT
                )
        );
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + this.id
                + ", name='" + this.name + '\''
                + ", login='" + this.login + '\''
                + ", email='" + this.email + '\''
                + ", created=" + this.createDate
                + '}';
    }
}
