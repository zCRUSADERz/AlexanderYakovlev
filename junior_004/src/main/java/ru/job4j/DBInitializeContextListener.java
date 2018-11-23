package ru.job4j;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;

public class DBInitializeContextListener implements ServletContextListener {
    private final Logger logger = Logger.getLogger(DBInitializeContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            this.logger.fatal("PostgreSQL jdbc driver not found.", e);
            throw new RuntimeException("PostgreSQL jdbc driver not found.", e);
        }
        this.checkDBExist();
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/dbstore",
                "postgres",
                "password")) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(
                        ""
                                + "CREATE TABLE IF NOT EXISTS users ("
                                + "id BIGSERIAL PRIMARY KEY,"
                                + "login CHARACTER VARYING UNIQUE NOT NULL,"
                                + "name CHARACTER VARYING NOT NULL,"
                                + "email CHARACTER VARYING NOT NULL,"
                                + "created_date TIMESTAMP WITH TIME ZONE DEFAULT LOCALTIMESTAMP"
                                + ")"
                );
            }
        } catch (SQLException ex) {
            this.logger.fatal("PostgreSQL DB initialize error.", ex);
            throw new RuntimeException("PostgreSQL DB initialize error.", ex);
        }
    }

    private void checkDBExist() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/",
                "postgres",
                "password")) {
            try (Statement checkStatement = connection.createStatement()) {
                try (ResultSet databases = checkStatement.executeQuery(
                        "SELECT datname FROM pg_database WHERE datname='dbstore'")) {
                    if (!databases.next()) {
                        connection.createStatement()
                                .execute("CREATE DATABASE dbstore");
                    }
                }
            }
        } catch (SQLException ex) {
            this.logger.fatal("PostgreSQL DB initialize error.", ex);
            throw new RuntimeException("PostgreSQL DB initialize error.", ex);
        }
    }
}
