package ru.job4j;

/**
 * Strig contains.
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 2.01.2018
 * @version 1.0
 */
public class StringContains {

    /**
     * Contains one string another or not.
     * @param origin - origin string.
     * @param sub - substring.
     * @return - true if origin string contains substring.
     */
    public boolean contains(String origin, String sub) {
        char[] originCharArray = origin.toCharArray();
        char[] subCharArray = sub.toCharArray();
        boolean result = false;
        for (int i = 0; i <= originCharArray.length - subCharArray.length; i++) {
            for (int j = 0; j < subCharArray.length; j++) {
                if (originCharArray[i + j] == subCharArray[j]) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }
}
