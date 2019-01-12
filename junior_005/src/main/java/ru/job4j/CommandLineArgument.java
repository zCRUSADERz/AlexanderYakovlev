package ru.job4j;

import java.util.Optional;
import java.util.function.Function;

public final class CommandLineArgument<T> {
    private final String key;
    private final String[] args;
    private final Function<String, T> valueFunc;

    public CommandLineArgument(final String key, final String[] args,
                               final Function<String, T> valueFunc) {
        this.key = key;
        this.args = args;
        this.valueFunc = valueFunc;
    }

    Optional<T> arg() {
        Optional<T> result = Optional.empty();
        for (int i = 0; i < this.args.length; i++) {
            if (this.key.equals(this.args[i])) {
                result = Optional.of(this.valueFunc.apply(this.args[++i]));
                break;
            }
        }
        return result;
    }
}
