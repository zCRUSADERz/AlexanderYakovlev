package ru.job4j.sort.external;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * PartFileIterator.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 18.01.2019
 */
public final class PartFileIterator implements Iterator<List<String>> {
    /**
     * Текстовый файл из которого читаем строки.
     */
    private final Path textFile;
    /**
     * Размер(в байтах) одного списка строк прочитанного из файла.
     */
    private final long partSize;
    /**
     * Позиция итератора в файле.
     */
    private long position;

    public PartFileIterator(final Path textFile, final long partSize) {
        this.textFile = textFile;
        this.partSize = partSize;
    }

    @Override
    public final boolean hasNext() {
        try {
            boolean result = true;
            if (Files.size(this.textFile) == this.position) {
                result = false;
            }
            return result;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * Читает следующий список строк из файла размером в partSize байт.
     * @return список строк из файла размером в partSize.
     */
    @Override
    public final List<String> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        try (final RandomAccessFile raf = new RandomAccessFile(this.textFile.toFile(), "r")) {
            raf.seek(this.position);
            final List<String> lines = new ArrayList<>();
            while ((raf.getFilePointer() - this.position) < this.partSize) {
                final String line = raf.readLine();
                if (Objects.isNull(line)) {
                    break;
                }
                lines.add(new String(
                        line.getBytes(StandardCharsets.ISO_8859_1),
                        StandardCharsets.UTF_8
                ));
            }
            this.position = raf.getFilePointer();
            return lines;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
