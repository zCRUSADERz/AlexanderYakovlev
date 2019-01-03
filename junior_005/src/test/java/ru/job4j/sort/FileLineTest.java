package ru.job4j.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Line in file. Test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public class FileLineTest {
    private final Path file = Path.of(
            ".", "src", "test",
            "resources", "FileLineTest.txt"
    );
    private RandomAccessFile access;
    private final Charset charset = StandardCharsets.UTF_8;
    private FileLine fileLine;

    @Before
    public void setUp() throws FileNotFoundException {
        this.access = new RandomAccessFile(this.file.toFile(), "r");
    }

    @After
    public void close() throws IOException {
        access.close();
    }

    private void setFileLine(long position, int bytes) {
        this.fileLine = new FileLine(
                this.file, path-> this.access, position, bytes, this.charset
        );
    }

    @Test
    public void bytesReturnBytesFromFile() {
        this.setFileLine(37, 15);
        this.fileLine.bytes();
        final byte[] expected = "на одной".getBytes(StandardCharsets.UTF_8);
        final byte[] result = this.fileLine.bytes();
        assertArrayEquals(result, expected);
    }

    @Test
    public void whenCopyToOutputStream() throws IOException {
        this.setFileLine(22, 14);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.fileLine.copy(out);
        final byte[] expected = "Юникода".getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(out.toByteArray(), expected);
    }

    @Test
    public void lineReturnString() throws IOException {
        this.setFileLine(0, 6);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.fileLine.copy(out);
        final String expected = "Все";
        final String result = this.fileLine.line();
        assertEquals(result, expected);
    }

    @Test (expected = IllegalStateException.class)
    public void whenFileLineNotExistInFileThenThrowException() {
        this.setFileLine(135, 176);
        this.fileLine.line();
    }
}