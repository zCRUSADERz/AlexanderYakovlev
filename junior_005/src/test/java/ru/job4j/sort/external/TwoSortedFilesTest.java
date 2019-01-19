package ru.job4j.sort.external;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Two sorted files, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.01.2019
 */
public class TwoSortedFilesTest {
    private final Path first = Path.of(
            ".", "src", "test", "resources",
            "twosortedfiles", "first.txt"
    );
    private final Path second = Path.of(
            ".", "src", "test", "resources",
            "twosortedfiles", "second.txt"
    );
    private final Comparator<String> lineComparator
            = Comparator.comparing(String::length);

    @Test (expected = IOException.class)
    public void whenFilesNotExistThenMergeThrowException() throws IOException {
        final Path dirPath = Path.of(
                ".", "src", "test", "resources"
        );
        new TwoSortedFiles(
                dirPath,
                dirPath,
                this.lineComparator
        ).merge(dirPath);
    }

    @Test
    public void whenMergeThenResultFileIsSorted() throws IOException {
        final Path tempDir = Path.of(System.getProperty("java.io.tmpdir"));
        final Path tempFile
                = Files.createTempFile(tempDir, "sorted", ".txt");
        new TwoSortedFiles(first, second, this.lineComparator).merge(tempFile);
        final List<String> expected = Arrays.asList(
                "1-g", "2-qb", "3-235", "4-245л", "5-qwevr", "6-зице16"
        );
        assertThat(Files.readAllLines(tempFile), is(expected));
        assertTrue(tempFile.toFile().delete());
    }
}