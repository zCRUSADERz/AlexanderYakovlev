package ru.job4j.find;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * FileInFileTree.
 * Файл находщийся где-то в файловом дереве определенной директории.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.02.2019
 */
public final class FileInFileTree {
    private final Path rootDir;
    private final Predicate<Path> filePathPredicate;

    public FileInFileTree(final Path rootDir,
                          final Predicate<Path> filePathPredicate) {
        this.rootDir = rootDir;
        this.filePathPredicate = filePathPredicate;
    }

    /**
     * Сохранить файл по указанному пути.
     * @param target путь по которому нужно сохранить файл.
     * @throws IOException если возникло исключение во время поиска,
     * либо записи в конечный файл.
     * @throws IllegalStateException если файл не был найден.
     */
    public final void save(final Path target) throws IOException {
        try (final Stream<Path> pathStream = Files.walk(this.rootDir)) {
            pathStream
                    .filter(path -> Files.isRegularFile(path))
                    .filter(this.filePathPredicate)
                    .findFirst()
                    .ifPresentOrElse(
                            file -> {
                                try {
                                    Files.copy(file, target);
                                } catch (IOException ex) {
                                    throw new UncheckedIOException(ex);
                                }
                            },
                            () -> {
                                throw new IllegalStateException("File not found");
                            }
                    );
        } catch (UncheckedIOException ex) {
            throw ex.getCause();
        }
    }
}
