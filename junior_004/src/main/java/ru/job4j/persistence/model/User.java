package ru.job4j.persistence.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class User {
    private long id;
    private String login;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createDate;

    public User() {
    }

    public User(String login,
                String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login,
                String password,
                String name,
                String email) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User(long id,
                String login,
                String password,
                String name,
                String email,
                LocalDateTime createDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createDate = createDate;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public boolean loginIsEqual(String login) {
        return this.login.equals(login);
    }

    public String shortCreateDate() {
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
                + ", login='" + this.login + '\''
                + ", name='" + this.name + '\''
                + ", email='" + this.email + '\''
                + ", created=" + this.createDate
                + '}';
    }
}
