package ru.job4j.sqlru.offers;

import org.apache.log4j.Logger;
import ru.job4j.sqlru.store.DBException;
import ru.job4j.sqlru.store.OfferStore;

import java.util.Date;
import java.util.function.Predicate;

/**
 * Неопределенная(образец) вакансия.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class OfferBlank {
    private final int offerId;
    private final String title;
    private final String text;
    private final Date created;
    private final String url;
    private final Logger logger = Logger.getLogger(OfferBlank.class);

    public OfferBlank(int offerId, String title,
                      String text, Date created, String url) {
        this.offerId = offerId;
        this.title = title;
        this.text = text;
        this.created = created;
        this.url = url;
    }

    /**
     * Проверяет, что данная вакансия не находится в хранилище вакансий.
     * @param store хранилище вакансий.
     * @return true, если вакансия не находится в хранилище.
     * @throws DBException if store throw DBException.
     */
    public boolean notInStore(OfferStore store) throws DBException {
        boolean result = !store.findById(this.offerId);
        if (!result) {
            this.logger.info(String.format(
                    "Вакансия с Id: %s - уже добавлена в БД.",
                    this.offerId
                    )
            );
        }
        return result;
    }

    /**
     * Проверяет, что вакансия создана позже переданной даты.
     * @param date дата.
     * @return true, если вакансия создана позже date.
     */
    public boolean isCreatedLaterThan(Date date) {
        boolean result = this.created.compareTo(date) > 0;
        if (!result) {
            this.logger.info(String.format(
                    "Вакансия была создана: %s, это раньше чем год назад: %s.",
                    this.created, date
                    )
            );
        }
        return result;
    }

    /**
     * Прроверить название вакансии на соответствие условиям описанным в
     * переданной функции.
     * @param predicate функция проверяющая на соответствие.
     * @return true, если проверка пройдена.
     */
    public boolean validateTitle(Predicate<String> predicate) {
        boolean result = predicate.test(this.title);
        if (!result) {
            this.logger.info(String.format(
                    "Вакансия не прошла валидацию. Вакансия: %s",
                    this.title
                    )
            );
        }
        return result;
    }

    /**
     * Создать вакансию с определенной специализацией(JavaOffer к примеру).
     * @param offers объект представляющий все вакансии одной специализации.
     * @param <T> созданная новая вакансия.
     * @return вакансия с определенной специальностью.
     */
    public <T extends SpecificOffer> T processToOffer(Offers<T> offers) {
        return offers.createOffer(
                this.offerId,
                this.title,
                this.text,
                this.created,
                url
        );
    }

    @Override
    public String toString() {
        return String.format(
                "Offer blank.%n"
                        + "OfferId: %d.%n"
                        + "Title: %s.%n"
                        + "Description: %s.%n"
                        + "Created: %s.%n"
                        + "Url: %s.%n",
                this.offerId, this.title, this.text, this.created, this.url
        );
    }
}
