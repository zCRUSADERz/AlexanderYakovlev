package ru.job4j.sort;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

/**
 * Line in file. Test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public class FileLineTest {

    @Test
    public void whenWriteLineToOutputStream() throws IOException {
        int bytes = 6 + System.lineSeparator().getBytes().length;
        final FileLine line = new FileLine(6, bytes);
        try (final RandomAccessFile randomAccessFile
                     = new RandomAccessFile(Path.of(
                             ".", "src", "test",
                "resources", "LineTestFile.txt"
        ).toFile(), "r")) {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            line.write(randomAccessFile, out);
            final String expected = String.format("789%n987");
            assertEquals(out.toString(StandardCharsets.UTF_8), expected);
        }
    }

    @Test
    public void whenFirstFileLineHasMoreBytesThanOtherThenFirstMoreThanOther() {
        final long pos = 4;
        final FileLine line = new FileLine(pos, 7);
        final FileLine other = new FileLine(pos, 2);
        final int expected = 1;
        assertEquals(expected, line.compareTo(other));
    }

    @Test
    public void whenDifferentPositionsAndFirstFileLineHasMoreBytesThanOtherThenFirstMoreThanOther() {
        final FileLine line = new FileLine(7, 7);
        final FileLine other = new FileLine(15, 2);
        final int expected = 1;
        assertEquals(expected, line.compareTo(other));
    }
}