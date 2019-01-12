package ru.job4j.zip;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * File directory.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 13.01.2019
 */
public final class FileDirectory {
    private final Path dirPath;
    /**
     * Функция возвращающая FileVisitor, который будет отправлять все
     * посещенные файлы или директории в переданный нами Consumer.
     */
    private final Function<Consumer<Path>, FileVisitor<Path>> fileVisitorFactory;

    public FileDirectory(
            final Path dirPath,
            final Function<Consumer<Path>, FileVisitor<Path>> fileVisitorFactory) {
        this.dirPath = dirPath;
        this.fileVisitorFactory = fileVisitorFactory;
    }

    /**
     * Создает новый архив текущего каталога
     * с всеми элементами посещенными FileVisitor.
     * @param targetPath target archive path.
     * @throws IOException если выброшено исключение во время создания нового архива.
     */
    public final void toZip(final Path targetPath) throws IOException {
        try (final ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(
                targetPath, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))) {
            final Consumer<Path> pathConsumer = (fileInDirectory) -> {
                try {
                    final Path pathInArchive
                            = this.dirPath.relativize(fileInDirectory);
                    out.putNextEntry(new ZipEntry(pathInArchive.toString()));
                    Files.copy(fileInDirectory, out);
                    out.closeEntry();
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            };
            try {
                Files.walkFileTree(
                        this.dirPath,
                        this.fileVisitorFactory.apply(pathConsumer)
                );
            } catch (UncheckedIOException ex) {
                throw ex.getCause();
            }
        }
    }
}
