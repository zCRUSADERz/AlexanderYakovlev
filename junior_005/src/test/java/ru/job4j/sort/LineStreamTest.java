package ru.job4j.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * File line stream. Test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public class LineStreamTest {
    private LineStream lineStream;

    @Before
    public void setUp() throws IOException {
        this.lineStream = new LineStream(
                new FileInputStream(
                        Path.of(
                                ".", "src", "test",
                                "resources", "LineTestFile.txt")
                                .toFile()
                )
        );
    }

    @After
    public void after() throws Exception {
        this.lineStream.close();
    }

    @Test
    public void whenReadTestFileWithTwoStringOnceThenReturnFirstFileLine() throws IOException {
        final Optional<FileLine> expected = Optional.of(
                new FileLine(0, 9 + System.lineSeparator().getBytes().length)
        );
        assertEquals(expected, this.lineStream.next());
    }

    @Test
    public void whenReadTestFileWithTwoStringTwiceThenReturnTwoFileLine() throws IOException {
        this.lineStream.next();
        int pos = 9 + System.lineSeparator().getBytes().length;
        final Optional<FileLine> expected = Optional.of(
                new FileLine(pos, 6)
        );
        assertEquals(expected, this.lineStream.next());
    }

    @Test
    public void whenReadTestFileWithTwoStringTriceThenLastReadReturnEmptyOptional() throws IOException {
        this.lineStream.next();
        this.lineStream.next();
        assertEquals(Optional.empty(), this.lineStream.next());
    }
}