package ru.job4j.sqlru.offers;

import ru.job4j.sqlru.utils.SimpleDate;
import ru.job4j.sqlru.store.DBException;
import ru.job4j.sqlru.store.OfferStore;

import java.util.Objects;

/**
 * Java offer.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class JavaOffer implements SpecificOffer {
    private final int id;
    private final String title;
    private final String text;
    private final SimpleDate created;
    private final String url;

    public JavaOffer(int id, String title,
                     String text, SimpleDate created, String url) {

        this.id = id;
        this.title = title;
        this.text = text;
        this.created = created;
        this.url = url;
    }

    /**
     * Save to store.
     * @param store java offer store.
     * @throws DBException if store throw DBException.
     */
    public void saveToStore(OfferStore store) throws DBException {
        store.save(this.id, this.title, this.text, this.created, this.url);
    }

    @Override
    public String toString() {
        return String.format(
                "Java offer.%n"
                        + "OfferId: %d.%n"
                        + "Title: %s.%n"
                        + "Description: %s.%n"
                        + "Created: %s.%n"
                        + "Url: %s.%n",
                this.id, this.title, this.text, this.created, this.url
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JavaOffer javaOffer = (JavaOffer) o;
        return id == javaOffer.id
                && Objects.equals(title, javaOffer.title)
                && Objects.equals(text, javaOffer.text)
                && Objects.equals(created, javaOffer.created)
                && Objects.equals(url, javaOffer.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, created, url);
    }
}
