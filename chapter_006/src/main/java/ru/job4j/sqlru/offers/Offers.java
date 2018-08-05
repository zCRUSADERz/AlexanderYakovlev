package ru.job4j.sqlru.offers;

import java.util.Date;

/**
 * Интерфейс для определенных вакансий(Java к примеру).
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public interface Offers<E extends SpecificOffer> {

    /**
     * Проверяет является ли данный образец вакансии специальности <E>.
     * @param blank образец вакансии.
     * @return true, если образец подходит под критерии данной специальности.
     */
    boolean isOffer(OfferBlank blank);

    /**
     * Создает из OfferBlank <E>Offer (JavaOffer ).
     * @param id offer id.
     * @param title offer title.
     * @param text offer description.
     * @param created offer created date.
     * @param url link to offer topic.
     * @return - <E>Offer.
     */
    E createOffer(int id, String title, String text, Date created, String url);
}
