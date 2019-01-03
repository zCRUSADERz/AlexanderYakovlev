package ru.job4j.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 * Sorted file lines. Test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public class SortedFileLinesTest {
    private final Path file = Path.of(
            ".", "src", "test",
            "resources", "TestText.txt"
    );
    private final Charset charset = StandardCharsets.UTF_8;
    private RandomAccessFile access;
    private final SortedFileLines sortedFileLines = new SortedFileLines(
            this.file,
            this.charset,
            "\r\n",
            path -> this.access
    );

    @Before
    public void setUp() throws FileNotFoundException {
        this.access = new RandomAccessFile(this.file.toFile(), "r");
    }

    @After
    public void close() throws IOException {
        this.access.close();
    }

    @Test
    public void whenWriteTestFileToOutputStreamThenLinesSorted() throws IOException {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            this.sortedFileLines.copy(out);
            assertEquals(
                    String.format(""
                            + "123%n"
                            + "2345%n"
                            + "356578%n"
                            + "46357375%n"
                            + "5¢Hello world! Aaaargh. aarg%n"
                    ),
                    out.toString(StandardCharsets.UTF_8)
            );
        }
    }
}