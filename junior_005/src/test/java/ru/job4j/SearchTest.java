package ru.job4j;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Search, test.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.01.2019
 */
public class SearchTest {
    private final Search search = new Search();
    private final Path root = Path.of(System.getProperty("java.io.tmpdir"));
    private Path tempDir;
    private Deque<Path> paths = new LinkedList<>();
    private Map<String, Path> files = new HashMap<>();

    private void create() throws IOException {
        this.tempDir = Files.createTempDirectory(root, "searchTest");
        this.paths.push(this.tempDir);
        final Path java = Files.createTempFile(this.tempDir, "Search", ".java");
        this.paths.push(java);
        this.files.put(".java", java);
        final Path firstDir = Files.createTempDirectory(this.tempDir, "first");
        this.paths.push(firstDir);
        final Path text = Files.createTempFile(firstDir, "text", ".txt");
        this.paths.push(text);
        this.files.put(".txt", text);
        final Path secondDir = Files.createTempDirectory(this.tempDir, "second");
        this.paths.push(secondDir);
        final Path jpg = Files.createTempFile(secondDir, "image", ".jpg");
        this.paths.push(jpg);
        this.files.put(".jpg", jpg);
        final Path firstInFirst = Files.createTempDirectory(firstDir, "first");
        this.paths.push(firstInFirst);
        final Path xml = Files.createTempFile(firstInFirst, "file", ".xml");
        this.paths.push(xml);
        this.files.put(".xml", xml);
        final Path firstInSecond = Files.createTempDirectory(secondDir, "first");
        this.paths.push(firstInSecond);
        final Path classFile = Files.createTempFile(firstInSecond, "Search", ".class");
        this.paths.push(classFile);
        this.files.put(".class", classFile);
        final Path firstInFirstInSecond = Files.createTempDirectory(firstInSecond, "first");
        this.paths.push(firstInFirstInSecond);
        final Path doc = Files.createTempFile(firstInFirstInSecond, "Search", ".doc");
        this.paths.push(doc);
        this.files.put(".doc", doc);
    }

    @After
    public void deleteTempDirectory() {
        while (!this.paths.isEmpty()) {
            this.paths.pop().toFile().delete();
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenParentNotExistThenThrowException() throws IOException {
        final Path path = Files.createTempDirectory(root, "searchTest");
        path.toFile().delete();
        this.search.files(path.toString(), Collections.emptyList());
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenParentIsNotDirectoryThenThrowException() throws IOException {
        final Path path = Files.createTempDirectory(root, "searchTest");
        path.toFile().delete();
        Files.createFile(path);
        this.paths.push(path);
        this.search.files(path.toString(), Collections.emptyList());
    }

    @Test
    public void whenNoOneHasSearchedExtensionThenReturnEmptyList() throws IOException {
        create();
        assertThat(
                this.search.files(this.tempDir.toString(), Collections.singletonList(".h")),
                Matchers.emptyIterable()
        );
    }

    @Test
    public void whenMultipleHasSearchedExtensionThenReturnFiles() throws IOException {
        create();
        assertThat(
                this.search.files(
                        this.tempDir.toString(),
                        Arrays.asList(".doc", ".xml", ".txt")),
                containsInAnyOrder(
                        this.files.get(".doc").toFile(),
                        this.files.get(".xml").toFile(),
                        this.files.get(".txt").toFile()
                )
        );
        assertThat(
                this.search.files(
                        this.tempDir.toString(),
                        Arrays.asList(".java", ".png", ".txt", ".h", ".class")),
                containsInAnyOrder(
                        this.files.get(".java").toFile(),
                        this.files.get(".txt").toFile(),
                        this.files.get(".class").toFile()
                )
        );
    }
}