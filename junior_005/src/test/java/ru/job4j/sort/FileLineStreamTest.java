package ru.job4j.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * File line stream. Test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 27.12.2018
 */
public class FileLineStreamTest {
    private FileLineStream lineStream;

    @Before
    public void setUp() throws FileNotFoundException {
        this.lineStream = new FileLineStream(FileLineTest.TEST_FILE_PATH);
    }

    @After
    public void after() throws Exception {
        this.lineStream.close();
    }

    @Test
    public void whenReadTestFileWithTwoStringOnceThenReturnFirstFileLine() {
        final Optional<FileLine> expected = Optional.of(new FileLine(
                FileLineTest.TEST_FILE_PATH, 0, 9
        ));
        assertEquals(expected, this.lineStream.next());
    }

    @Test
    public void whenReadTestFileWithTwoStringTwiceThenReturnTwoFileLine() {
        this.lineStream.next();
        final Optional<FileLine> expected = Optional.of(new FileLine(
                FileLineTest.TEST_FILE_PATH, 11, 6
        ));
        assertEquals(expected, this.lineStream.next());
    }

    @Test
    public void whenReadTestFileWithTwoStringTriceThenLastReadReturnEmptyOptional() {
        this.lineStream.next();
        this.lineStream.next();
        assertEquals(Optional.empty(), this.lineStream.next());
    }
}