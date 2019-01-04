package ru.job4j.bot;

import ru.job4j.sort.FileLines;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Entrance.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public class Entrance {

    public static void main(String[] args) throws IOException {
        final Path answersFile = Path.of("D:\\chatBot.txt");
        try (final RandomAccessFile accessFile = new RandomAccessFile(answersFile.toFile(), "r")) {
            final AtomicBoolean stopped = new AtomicBoolean(false);
            final AtomicBoolean silence = new AtomicBoolean(false);
            final PrintWriter writer = new BotLogger(System.out);
            new ChatBot(
                    new UserLogger(new InputStreamReader(System.in)),
                    writer, stopped,
                    Map.ofEntries(
                            new AbstractMap.SimpleImmutableEntry<>(
                                    "стоп",
                                    new Stop(
                                            silence,
                                            writer
                                    )
                            ),
                            new AbstractMap.SimpleImmutableEntry<>(
                                    "продолжить",
                                    new Continue(
                                            silence,
                                            writer
                                    )
                            ),
                            new AbstractMap.SimpleImmutableEntry<>(
                                    "закончить",
                                    new Finish(
                                            stopped,
                                            writer
                                    )
                            )
                    ),
                    new Answer(
                            silence,
                            new FileLines(
                                    answersFile,
                                    StandardCharsets.UTF_8,
                                    path -> accessFile
                            ).lines(),
                            writer
                    )
            ).start();
        }

    }
}
