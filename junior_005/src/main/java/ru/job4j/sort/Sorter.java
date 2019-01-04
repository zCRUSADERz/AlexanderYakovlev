package ru.job4j.sort;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Sorter.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public final class Sorter {

    /**
     * Write the sorted lines in ascending order from source to dest.
     * @param source source file.
     * @param dest file with sorted lines.
     * @throws FileNotFoundException if source file not found.
     */
    public static void sort(File source, File dest) throws IOException {
        Files.deleteIfExists(dest.toPath());
        Files.createFile(dest.toPath());
        try (final OutputStream out
                     = new BufferedOutputStream(new FileOutputStream(dest));
             final RandomAccessFile randomAccess
                     = new RandomAccessFile(source, "r")) {
            new SortedFileLines(
                    source.toPath(),
                    StandardCharsets.UTF_8,
                    path -> randomAccess
            ).copy(out);
        }
    }
}