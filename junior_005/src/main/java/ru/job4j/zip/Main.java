package ru.job4j.zip;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException {
        Optional<Path> directory = Optional.empty();
        Optional<PathMatcher> matcher = Optional.empty();
        Optional<Path> archivePath = Optional.empty();
        for (int i = 0; i < args.length; i++) {
            if ("-d".equals(args[i])) {
                directory = Optional.of(Path.of(args[++i]));
            } else if ("-e".equals(args[i])) {
                matcher = Optional.of(FileSystems.getDefault()
                        .getPathMatcher(String.format("glob:*.{%s}", args[++i])));
            } else if ("-o".equals(args[i])) {
                archivePath = Optional.of(Path.of(args[++i]));
            }
        }
        if (directory.isEmpty()) {
            throw new IllegalStateException(
                    "Directory path missing in command line arguments"
            );
        }
        if (matcher.isEmpty()) {
            throw new IllegalStateException(
                    "Extensions missing in command line arguments"
            );
        }
        if (archivePath.isEmpty()) {
            throw new IllegalStateException(
                    "Target archive path missing in command line arguments"
            );
        }
        try (final ZipArchive zipArchive = new ZipArchive(archivePath.get())) {
            final Path dirPath = directory.get();
            Files.walkFileTree(
                    dirPath,
                    new FilteredFileVisitor(
                            matcher.get(),
                            path -> zipArchive.add(path, dirPath.relativize(path))
                    )
            );
        }

    }
}
