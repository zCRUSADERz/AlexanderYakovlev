package ru.job4j.sort;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * File line positions.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 06.01.2019
 */
public final class FileLinePositions<T> {
    private final Path path;
    private final Charset charset;
    private final int separatorBytes;
    private final BiFunction<String, Long, T> mapFunc;


    public FileLinePositions(final Path path, final Charset charset,
                             final BiFunction<String, Long, T> mapFunc) {
        this.path = path;
        this.charset = charset;
        this.separatorBytes
                = System.lineSeparator().getBytes(this.charset).length;
        this.mapFunc = mapFunc;
    }

    /**
     * Конвертирует поток строк из файла и вычисляет местоположение строки
     * в файле.
     *
     * Stream<T> необходимо использовать в связке с try-with-resources! Поток
     * содержит ссылку на открытый файловый поток.
     *
     * @return конвертированный поток строк с вычисленным положением в фале.
     */
    public final Stream<T> positions() {
        try {
            return Files
                    .lines(this.path, this.charset)
                    .sequential()
                    .map(new Function<>() {
                        private long previousPosition = 0;
                        @Override
                        public T apply(String line) {
                            final int lineBytes = line.getBytes(charset).length;
                            final T result
                                    = mapFunc.apply(line, this.previousPosition);
                            previousPosition += lineBytes + separatorBytes;
                            return result;
                        }
                    });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
