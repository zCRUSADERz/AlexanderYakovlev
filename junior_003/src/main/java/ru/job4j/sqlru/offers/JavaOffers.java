package ru.job4j.sqlru.offers;

import org.apache.log4j.Logger;
import ru.job4j.sqlru.utils.SimpleDate;

import java.util.function.Predicate;

/**
 * Java offers.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class JavaOffers implements Offers<JavaOffer> {
    private final Logger logger = Logger.getLogger(JavaOffers.class);

    /**
     * Проверяет является ли образец Java вакансией.
     * @param blank образец вакансии.
     * @return true, если является.
     */
    public boolean isOffer(Offer blank) {
        return blank.validateTitle(validator());
    }

    /**
     * Создать из образца объект JavaOffer.
     * @param id offer id.
     * @param title offer title.
     * @param text offer description.
     * @param created offer created date.
     * @param url link to offer topic.
     * @return JavaOffer.
     */
    @Override
    public JavaOffer createOffer(int id, String title, String text, SimpleDate created, String url) {
        if (!validator().test(title)) {
            this.logger.fatal("Is not java offer title.");
            throw new IllegalArgumentException("Is not java offer title.");
        }
        return new JavaOffer(id, title, text, created, url);
    }

    private Predicate<String> validator() {
        return s -> s.toLowerCase()
                .replace("javascript", "")
                .replace("java script", "")
                .contains("java");
    }
}
