package ru.job4j.sqlru.parsers;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.sqlru.utils.SimpleDate;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SqlRuTopicParser implements Iterable<OfferTopic> {
    private final Logger logger = Logger.getLogger(SqlRuTopicParser.class);

    @Override
    public Iterator<OfferTopic> iterator() {
        return new OfferTopicsIterator(new PagesIterator());
    }

    private Elements parseNextPage(String url) throws ParseException {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements allTopic = doc.select(
                    "table.forumTable > tbody > tr"
            ).next();
            return justOfferTopics(allTopic);
        } catch (IOException e) {
            String msg = String.format("Can't load sql.ru site. Url: %s", url);
            logger.fatal(msg, e);
            throw new ParseException(msg, e);
        }
    }

    private static Elements justOfferTopics(Elements allTopic) {
        Boolean offersWithInfoTopics = true;
        do {
            Element nextTopic = allTopic.first();
            String topicTitle = nextTopic.selectFirst("td.postslisttopic").text();
            if (topicTitle.contains("Важно:")) {
                allTopic = allTopic.next();
            } else {
                offersWithInfoTopics = false;
            }
        } while (offersWithInfoTopics);
        return allTopic;
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
                = "http://www.sql.ru/forum/job-offers/";
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
    }

    /**
     * Итератор ссылок на вакансии.
     * Страницы с ссылками получает от итератора страниц.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 04.08.2018
     */
    private class OfferTopicsIterator implements Iterator<OfferTopic> {
        /**
         * Pages iterator.
         */
        private final Iterator<Elements> pagesIterator;
        /**
         * Links elements on page iterator.
         */
        private Iterator<Element> offerTopics;
        /**
         * true, if loaded next page.
         */
        private boolean loaded = false;

        public OfferTopicsIterator(final Iterator<Elements> pagesIterator) {
            this.pagesIterator = pagesIterator;
        }

        @Override
        public boolean hasNext() {
            if (!loaded) {
                loadNextLinks();
            }
            return this.offerTopics.hasNext();
        }

        @Override
        public OfferTopic next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration has no more elements");
            }
            Element topicElements = this.offerTopics.next();
            String url = topicElements.selectFirst("a").attr("href");
            String updated = topicElements.select("td[style].altCol").text();
            if (!this.offerTopics.hasNext()) {
                this.loaded = false;
            }
            return new OfferTopic(
                    url, new SimpleDate(new DateParser(updated).parse()));
        }

        private void loadNextLinks() {
            if (this.pagesIterator.hasNext()) {
                this.offerTopics = this.pagesIterator.next().iterator();
                this.loaded = true;
            }
        }
    }
}
