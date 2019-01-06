package ru.job4j.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

/**
 * File next stream. Test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public class FileLinesTest {
    private final Path file = Path.of(
            ".", "src", "test",
            "resources", "FileLinesTest.txt"
    );
    private final Charset charset = StandardCharsets.UTF_8;
    private RandomAccessFile access;
    private final FileLines fileLines = new FileLines(
            Path.of(
                    ".", "src", "test",
                    "resources", "FileLinesTest.txt"
            ),
            this.charset,
            path -> this.access

    );

    @Before
    public void setUp() throws FileNotFoundException {
        this.access = new RandomAccessFile(this.file.toFile(), "r");
    }

    @After
    public void close() throws IOException {
        access.close();
    }

    @Test
    public void linesReturnFileLines() {
        assertThat(
                this.fileLines.lines(),
                contains(
                        new FileLine(
                                this.file,
                                path -> this.access,
                                0,
                                9,
                                this.charset
                        ),
                        new FileLine(
                                this.file,
                                path -> this.access,
                                9 + System.lineSeparator().getBytes(this.charset).length,
                                6,
                                this.charset
                        )
                )
        );
    }
}