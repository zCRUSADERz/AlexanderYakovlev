package ru.job4j.zip;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipArchive implements AutoCloseable {
    private final ZipOutputStream out;

    public ZipArchive(final Path archivePath) throws IOException {
        this.out = new ZipOutputStream(Files.newOutputStream(
                archivePath,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE
        ));
    }

    public final void add(final Path filePath, final Path archiveFilePath) {
        try {
            out.putNextEntry(new ZipEntry(archiveFilePath.toString()));
            Files.copy(filePath, out);
            out.closeEntry();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
