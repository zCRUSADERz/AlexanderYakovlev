package ru.job4j.switcher;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Number string.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 25.03.2019
 */
public class NumberString {
    private final AtomicReference<String> line;

    public NumberString() {
        this(new AtomicReference<>(""));
    }

    public NumberString(final AtomicReference<String> line) {
        this.line = line;
    }

    /**
     * Attach number to end of line.
     * @param number number.
     */
    public final void attach(final int number) {
        this.line.accumulateAndGet(
                Integer.toString(number),
                (previous, attachedNumber) -> String.format(
                        "%s%s", previous, attachedNumber
                )
        );
    }

    /**
     * @return number string.
     */
    public final String string() {
        return this.line.get();
    }
}
