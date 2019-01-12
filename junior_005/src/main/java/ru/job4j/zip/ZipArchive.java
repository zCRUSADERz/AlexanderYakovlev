package ru.job4j.zip;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipArchive {
    private final Path archivePath;

    public ZipArchive(final Path archivePath) {
        this.archivePath = archivePath;
    }

    public final void add(final Path filePath, final Path archiveFilePath) {
        try (final ZipOutputStream out = new ZipOutputStream(
                new FileOutputStream(this.archivePath.toFile()))) {
            out.putNextEntry(new ZipEntry(archiveFilePath.toString()));
            Files.copy(filePath, out);
            out.closeEntry();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
