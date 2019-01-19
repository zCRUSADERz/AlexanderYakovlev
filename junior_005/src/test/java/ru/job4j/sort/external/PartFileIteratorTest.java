package ru.job4j.sort.external;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Part file iterator, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.01.2019
 */
public class PartFileIteratorTest {
    private final Path text = Path.of(
            ".", "src", "test", "resources",
            "partfileiterator", "Text.txt"
    );
    private final Path emptyFile = Path.of(
            ".", "src", "test", "resources",
            "partfileiterator", "Empty.txt"
    );

    @Test
    public void whenFileNotEmptyThenHasNextReturnTrue() {
        final PartFileIterator iterator
                = new PartFileIterator(this.text, 1024);
        assertTrue(iterator.hasNext());
    }

    @Test
    public void whenFileEmptyThenHasNextReturnFalse() {
        final PartFileIterator iterator
                = new PartFileIterator(this.emptyFile, 1024);
        assertFalse(iterator.hasNext());
    }

    @Test (expected = NoSuchElementException.class)
    public void whenHasNextReturnFalseThenNextThrowNoSuchElementException() {
        final PartFileIterator iterator
                = new PartFileIterator(this.emptyFile, 1024);
        assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test
    public void whenNextThenStringsHaveBytesSizeEqualOrMoreThenPartSize() {
        final long partSize = String.format("0-null%n")
                .getBytes(StandardCharsets.UTF_8).length;
        final PartFileIterator iterator
                = new PartFileIterator(this.text, partSize);
        assertThat(iterator.next(), is(Collections.singletonList("0-null")));
        assertThat(iterator.next(), is(Collections.singletonList("1-привет")));
    }

    @Test
    public void whenReadAllTextThenReturnThreeLists() {
        final long partSize = String.format("0-null%n1-привет")
                .getBytes(StandardCharsets.UTF_8).length;
        final PartFileIterator iterator
                = new PartFileIterator(this.text, partSize);
        assertThat(iterator.next(), is(Arrays.asList("0-null", "1-привет")));
        assertThat(
                iterator.next(),
                is(Arrays.asList("2-hello world", "3-iterator"))
        );
        assertThat(iterator.next(), is(Collections.singletonList("4-part file")));
        assertFalse(iterator.hasNext());
    }
}