package ru.job4j.sqlru.parsers;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Парсер ссылок на страницу с вакансией.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuOfferLinksParser implements Iterable<String> {
    private final Logger logger = Logger.getLogger(SqlRuOfferLinksParser.class);

    /**
     * Iterator URL links as String.
     * @return iterator.
     */
    @Override
    public Iterator<String> iterator() {
        return new OffersLinkIterator(new PagesIterator());
    }

    private Elements parseNextPage(String url) throws ParseException {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.select(
                    "td.postslisttopic > a[href^=\"http\"]"
            );
        } catch (IOException e) {
            String msg = String.format("Can't load sql.ru site. Url: %s", url);
            logger.fatal(msg, e);
            throw new ParseException(msg, e);
        }
    }

    /**
     * Итератор страниц с ссылками.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 04.08.2018
     */
    private class PagesIterator implements Iterator<Elements> {
        /**
         * Ссылка на результаты поисковой системы форума sql.ru.
         */
        private static final String URL
                = "http://www.sql.ru/forum/actualsearch.aspx?"
                + "search=java&sin=1&bid=66&a=&ma=0&dt=-1&s=4&so=1&pg=";
        private int page = 1;
        /**
         * Next page HTML-elements.
         */
        private Elements loadedElements;
        /**
         * true, if next page loaded.
         */
        private boolean loaded = false;

        @Override
        public boolean hasNext() {
            if (!this.loaded) {
                try {
                    this.loadedElements = parseNextPage(URL + page++);
                    this.loaded = true;
                } catch (ParseException e) {
                    throw new IllegalStateException(e);
                }
            }
            return !this.loadedElements.isEmpty();
        }

        @Override
        public Elements next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration has no more elements");
            }
            this.loaded = false;
            return this.loadedElements;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation unsupported.");
        }
    }

    /**
     * Итератор ссылок на вакансии.
     * Страницы с ссылками получает от итератора страниц.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 04.08.2018
     */
    private class OffersLinkIterator implements Iterator<String> {
        /**
         * Pages iterator.
         */
        private final Iterator<Elements> pagesIterator;
        /**
         * Links elements on page iterator.
         */
        private Iterator<Element> offerLinks;
        /**
         * true, if loaded next page.
         */
        private boolean loaded = false;

        public OffersLinkIterator(final Iterator<Elements> pagesIterator) {
            this.pagesIterator = pagesIterator;
        }

        @Override
        public boolean hasNext() {
            if (!loaded) {
                loadNextLinks();
            }
            return this.offerLinks.hasNext();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration has no more elements");
            }
            String result = this.offerLinks.next().attr("href");
            if (!this.offerLinks.hasNext()) {
                this.loaded = false;
            }
            return result;
        }

        private void loadNextLinks() {
            if (this.pagesIterator.hasNext()) {
                this.offerLinks = this.pagesIterator.next().iterator();
                this.loaded = true;
            }
        }
    }
}
