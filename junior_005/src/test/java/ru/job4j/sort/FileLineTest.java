package ru.job4j.sort;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

/**
 * Line in file. Test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public class FileLineTest {
    public final static Path TEST_FILE_PATH = Path.of(
            ".", "src", "test",
            "resources", "LineTestFile.txt"
    );

    @Test
    public void whenWriteLineToOutputStream() throws IOException {
        final FileLine line = new FileLine(TEST_FILE_PATH, 6, 8);
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            line.write(out);
            final String expected = String.format("789%n987%n");
            assertEquals(out.toString(), expected);
        }
    }

    @Test (expected = IllegalStateException.class)
    public void whenFileNotExistThenThrowException() throws IOException {
        final FileLine line = new FileLine(Path.of(""), 6, 8);
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            line.write(out);
        }
    }

    @Test (expected = IllegalStateException.class)
    public void whenOutputClosedThenThrowException() throws IOException {
        final FileLine line = new FileLine(Path.of(""), 6, 8);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.close();
        line.write(out);
    }

    @Test (expected = IllegalStateException.class)
    public void whenPositionBeyondOfTheEndFileThenThrowException() throws IOException {
        final FileLine line = new FileLine(TEST_FILE_PATH, 30, 1);
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            line.write(out);
        }
    }

    @Test
    public void whenFirstFileLineHasMoreBytesThanOtherThenFirstMoreThanOther() {
        final Path path = Path.of("");
        final long pos = 4;
        final FileLine line = new FileLine(path, pos, 7);
        final FileLine other = new FileLine(path, pos, 2);
        final int expected = 1;
        assertEquals(expected, line.compareTo(other));
    }

    @Test
    public void whenDifferentPositionsAndFirstFileLineHasMoreBytesThanOtherThenFirstMoreThanOther() {
        final Path path = Path.of("");
        final FileLine line = new FileLine(path, 7, 7);
        final FileLine other = new FileLine(path, 15, 2);
        final int expected = 1;
        assertEquals(expected, line.compareTo(other));
    }
}