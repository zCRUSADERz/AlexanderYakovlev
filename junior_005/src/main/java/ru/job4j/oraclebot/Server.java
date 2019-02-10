package ru.job4j.oraclebot;

import ru.job4j.oraclebot.oracle.OracleImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server app, for oracle bot.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 10.02.2019
 */
public final class Server implements Runnable {
    private final String port;

    public Server(final String port) {
        this.port = port;
    }

    @Override
    public final void run() {
        try (Socket socket
                     = new ServerSocket(Integer.parseInt(this.port)).accept()) {
            new Bot(
                    socket.getInputStream(),
                    new OracleImpl(),
                    socket.getOutputStream()
            ).run();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static void main(String[] args) {
        new Server("45152").run();
    }
}
