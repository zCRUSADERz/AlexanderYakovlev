package ru.job4j.sqlru.parsers.filters;

import ru.job4j.sqlru.parsers.OfferTopic;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Темы обновленные не раньше определенной даты.
 * Получив тему созданную раньше не учитывает оставшиеся темы.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class TopicsUpdatedNotEarlierThan implements Iterable<OfferTopic> {
    private final Iterable<OfferTopic> sortedByUpdateTopics;
    private final Date updatedNotEarlier;

    public TopicsUpdatedNotEarlierThan(
            final Iterable<OfferTopic> sortedByUpdateTopics, final Date updatedNotEarlier) {
        this.sortedByUpdateTopics = sortedByUpdateTopics;
        this.updatedNotEarlier = updatedNotEarlier;
    }


    @Override
    public Iterator<OfferTopic> iterator() {
        return new TopicIterator(
                this.sortedByUpdateTopics.iterator(),
                OfferTopic -> OfferTopic.isUpdatedLaterThan(this.updatedNotEarlier)
        );
    }

    /**
     * Итератор, который при получении темы обновленной
     * ранее определенной даты прекращает работу.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 04.08.2018
     */
    private class TopicIterator implements Iterator<OfferTopic> {
        private final Iterator<OfferTopic> topicIterator;
        private final Predicate<OfferTopic> filter;
        private OfferTopic nextTopic;
        private boolean receivedNext = false;
        private boolean nextTopicUpdatedEarlier = false;

        public TopicIterator(
                final Iterator<OfferTopic> topicIterator,
                final Predicate<OfferTopic> filter) {
            this.topicIterator = topicIterator;
            this.filter = filter;
        }

        @Override
        public boolean hasNext() {
            if (!this.nextTopicUpdatedEarlier && !this.receivedNext) {
                receiveNextOffer();
            }
            return this.receivedNext;
        }

        @Override
        public OfferTopic next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration has no more elements");
            }
            this.receivedNext = false;
            return this.nextTopic;
        }

        private void receiveNextOffer() {
            while (!this.receivedNext && !this.nextTopicUpdatedEarlier
                    && this.topicIterator.hasNext()) {
                OfferTopic offer = this.topicIterator.next();
                if (filter.test(offer)) {
                    this.nextTopic = offer;
                    this.receivedNext = true;
                } else {
                    this.nextTopicUpdatedEarlier = true;
                }
            }
        }
    }
}
