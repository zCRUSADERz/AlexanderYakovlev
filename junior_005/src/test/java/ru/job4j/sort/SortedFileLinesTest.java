package ru.job4j.sort;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @Test
    public void whenWriteTestFileToOutputStreamThenLinesSorted() throws IOException {
        final SortedFileLines sortedFileLines
                = new SortedFileLines(Path.of(
                ".", "src", "test",
                "resources", "TestText.txt"
        ));
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            sortedFileLines.copy(out);
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