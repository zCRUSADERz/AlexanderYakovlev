package ru.job4j;

import java.util.Optional;

public final class SafeCommandLineArg<T> {
    private final CommandLineArgument<T> cmdArgument;
    private final String errorDescription;

    public SafeCommandLineArg(final CommandLineArgument<T> cmdArgument,
                              final String errorDescription) {
        this.cmdArgument = cmdArgument;
        this.errorDescription = errorDescription;
    }

    public final T arg() {
        final Optional<T> optArg = this.cmdArgument.arg();
        if (optArg.isEmpty()) {
            throw new IllegalStateException(this.errorDescription);
        }
        return optArg.get();
    }
}
