package ru.job4j.sort.external;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Sorted files, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.01.2019
 */
public class SortedFilesTest {
    private final Path first = Path.of(
            ".", "src", "test", "resources",
            "sortedfiles", "first.txt"
    );
    private final List<String> firstLines = Arrays.asList(
            "1-g", "3-235", "4-245л"
    );
    private final Path second = Path.of(
            ".", "src", "test", "resources",
            "sortedfiles", "second.txt"
    );
    private final List<String> secondLines = Arrays.asList(
            "2-qb", "5-qwevr", "6-зице16"
    );
    private final Path third = Path.of(
            ".", "src", "test", "resources",
            "sortedfiles", "third.txt"
    );
    private final List<String> thirdLines = Arrays.asList(
            "2-и5", "4-г12д", "7-m435ф9у", "10-й63иве9qme"
    );
    private final Path tempDir = Path.of(System.getProperty("java.io.tmpdir"));
    private final Comparator<String> lineComparator
            = Comparator.comparing(String::length);

    @Test
    public void whenTwoWayMergeThreeFiles() throws IOException {
        final Path tempFile = this.tempFile();
        new SortedFiles(
                Arrays.asList(this.first, this.second, this.third),
                lineComparator
        ).twoWayMerge(this.tempDir, tempFile);
        check(
                Arrays.asList(this.firstLines, this.secondLines, this.thirdLines),
                tempFile
        );
    }

    @Test
    public void whenTwoWayMergeTwoFiles() throws IOException {
        final Path tempFile = this.tempFile();
        new SortedFiles(
                Arrays.asList(this.second, this.third),
                lineComparator
        ).twoWayMerge(this.tempDir, tempFile);
        check(
                Arrays.asList(this.secondLines, this.thirdLines),
                tempFile
        );
    }

    @Test
    public void whenTwoWayMergeOneFile() throws IOException {
        final Path tempFile = this.tempFile();
        new SortedFiles(
                Collections.singletonList(this.third),
                lineComparator
        ).twoWayMerge(this.tempDir, tempFile);
        check(
                Collections.singletonList(this.thirdLines),
                tempFile
        );
    }

    @Test
    public void whenMultiWayMergeThreeFiles() throws IOException {
        final Path tempFile = this.tempFile();
        new SortedFiles(
                Arrays.asList(this.third, this.second, this.first),
                lineComparator
        ).multiWayMerge(tempFile);
        check(
                Arrays.asList(this.secondLines, this.firstLines, this.thirdLines),
                tempFile
        );
    }

    @Test
    public void whenMultiWayMergeTwoFiles() throws IOException {
        final Path tempFile = this.tempFile();
        new SortedFiles(
                Arrays.asList(this.second, this.first),
                lineComparator
        ).multiWayMerge(tempFile);
        check(
                Arrays.asList(this.secondLines, this.firstLines),
                tempFile
        );
    }

    @Test
    public void whenMultiWayMergeOneFile() throws IOException {
        final Path tempFile = this.tempFile();
        new SortedFiles(
                Collections.singletonList(this.second),
                lineComparator
        ).multiWayMerge(tempFile);
        check(
                Collections.singletonList(this.secondLines),
                tempFile
        );
    }

    private void check(final List<List<String>> lines, final Path tempFile) throws IOException {
        final List<Integer> expected = lineLenghts(lines);
        final List<Integer> result = Files.readAllLines(tempFile, UTF_8)
                .stream()
                .map(String::length)
                .collect(Collectors.toList());
        assertThat(result, is(expected));
        Files.delete(tempFile);
    }

    private List<Integer> lineLenghts(Iterable<List<String>> lines) {
        final Queue<Stream<String>> queue = new LinkedList<>();
        for (List<String> list : lines) {
            queue.offer(list.stream());
        }
        while (queue.size() > 1) {
            final Stream<String> first = queue.poll();
            final Stream<String> second = queue.poll();
            queue.offer(Stream.concat(first, second));
        }
        return queue.poll().map(String::length).sorted().collect(Collectors.toList());
    }

    private Path tempFile() throws IOException {
        final Path tempFile = Files.createTempFile(
                this.tempDir, "sortedDest", ".txt"
        );
        Files.delete(tempFile);
        return tempFile;
    }
}