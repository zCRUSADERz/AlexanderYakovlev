package ru.job4j.oraclebot;

import ru.job4j.oraclebot.oracle.OracleServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client app, for oracle bot.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public final class Client implements Runnable {
    private final String ip;
    private final String port;

    public Client(final String ip, final String port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public final void run() {
        try (final Socket socket = new Socket(
                InetAddress.getByName(this.ip), Integer.parseInt(this.port))) {
            System.out.println("To exit the application, enter \"exit\"");
            System.out.println("You can ask any questions oracle.");
            new Bot(
                    System.in,
                    new OracleServer(socket.getInputStream(), socket.getOutputStream()),
                    System.out
            ).run();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", "45152").run();
    }
}
