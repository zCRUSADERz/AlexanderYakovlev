package ru.job4j.zip;

import ru.job4j.CommandLineArgument;
import ru.job4j.SafeCommandLineArg;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        new FileDirectory(
                new SafeCommandLineArg<>(
                        new CommandLineArgument<>(
                                "-d",
                                args,
                                value -> Path.of(value)
                        ),
                        "Directory path missing in command line arguments"
                ).arg(),
                pathConsumer -> new FilteredFileVisitor(
                        new SafeCommandLineArg<>(
                                new CommandLineArgument<>(
                                        "-e",
                                        args,
                                        value -> FileSystems.getDefault()
                                                .getPathMatcher(String.format("glob:*.{%s}", value))
                                ),
                                "Extensions missing in command line arguments"
                        ).arg(),
                        pathConsumer
                )
        ).toZip(
                new SafeCommandLineArg<>(
                        new CommandLineArgument<>(
                                "-o",
                                args,
                                value -> Path.of(value)
                        ),
                        "Target archive path missing in command line arguments"
                ).arg()
        );
    }
}
