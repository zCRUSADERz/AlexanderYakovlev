package ru.job4j;

import java.io.IOException;
import java.io.InputStream;

/**
 * Number service.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.12.2018
 */
public class NumberService {

    /**
     * Checks that the InputStream contains an even number.
     * @param in InputStream.
     * @return true if inputStream contains an even number.
     * @throws IOException if InputStream throw IOException.
     */
    public boolean isNumber(InputStream in) throws IOException {
        return new String(in.readAllBytes())
                .matches("(^[-+]?[02468]$)|(^[-+]?[1-9]+[0-9]*[02468]+$)");
    }
}
