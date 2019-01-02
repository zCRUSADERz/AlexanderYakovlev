package ru.job4j.zip;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

public final class FilteredFileVisitor extends SimpleFileVisitor<Path> {
    private final PathMatcher fileNameMatcher;
    private final Consumer<Path> filePathConsumer;

    public FilteredFileVisitor(final PathMatcher fileNameMatcher,
                               final Consumer<Path> filePathConsumer) {
        this.fileNameMatcher = fileNameMatcher;
        this.filePathConsumer = filePathConsumer;
    }

    @Override
    public FileVisitResult visitFile(final Path file,
                                     final BasicFileAttributes attrs)  {
        if (this.fileNameMatcher.matches(file.getFileName())) {
            filePathConsumer.accept(file);
        }
        return FileVisitResult.CONTINUE;
    }
}
