package ru.job4j.sqlru.parsers;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.job4j.sqlru.offers.OfferBlank;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Парсер вакансий с форума sql.ru.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuOffersParser implements Iterable<OfferBlank> {
    private final Iterable<String> offerLinks;
    private final Logger logger = Logger.getLogger(SqlRuOffersParser.class);

    public SqlRuOffersParser(final Iterable<String> offerLinks) {
        this.offerLinks = offerLinks;
    }

    /**
     * Parsing offer data.
     * @param url offer topic url.
     * @return Offer blank.
     * @throws ParseException if can't load site, or parsing error.
     */
    private OfferBlank parseOfferTopic(String url) throws ParseException {
        try {
            Document doc = Jsoup.connect(url).get();
            Element message = doc.selectFirst("table.msgTable");
            int id = Integer.parseInt(
                    message.selectFirst("td.msgFooter > a").ownText()
            );
            String title = message.selectFirst("td.messageHeader").ownText();
            String text = message.selectFirst("td.msgBody + td.msgBody").html();
            Date created = parseCreatedDate(
                    message.selectFirst("td.msgFooter").ownText()
            );
            return new OfferBlank(id, title, text, created, url);
        } catch (IOException e) {
            this.logger.fatal(String.format("Failed load site: %s", url), e);
            throw new ParseException(
                    String.format("Failed load site: %s", url), e
            );
        } catch (java.text.ParseException e) {
            this.logger.fatal("Parse created date error.", e);
            throw new ParseException("Parse created date error.", e);
        }
    }

    /**
     * Собирает с сайта дату создания вакансии.
     * Преобразует нестандартные даты: "сегодня" и "вчера" к стандартноу виду.
     * @param line created date string.
     * @return parsed created date.
     * @throws java.text.ParseException if wrong date format.
     */
    private Date parseCreatedDate(String line) throws java.text.ParseException {
        Date result;
        line = line.substring(0, line.indexOf("[")).trim();
        if (line.contains("сегодня")) {
            line = line.replace("сегодня, ", "");
            Calendar today = parseUnformattedDate(line);
            result = today.getTime();
        } else if (line.contains("вчера")) {
            line = line.replace("вчера, ", "");
            Calendar today = parseUnformattedDate(line);
            today.roll(Calendar.DAY_OF_MONTH, false);
            result = today.getTime();
        } else {
            result = new SimpleDateFormat(
                    "d MMM yy, HH:mm",
                    new Locale("ru")
            ).parse(line);
        }
        return result;
    }

    private Calendar parseUnformattedDate(String line) {
        GregorianCalendar today = new GregorianCalendar(
                TimeZone.getTimeZone("Europe/Moscow")
        );
        today.set(Calendar.HOUR_OF_DAY, parseHour(line));
        today.set(Calendar.MINUTE, parseMinute(line));
        return today;
    }

    private int parseHour(String line) {
        return Integer.parseInt(line.substring(0, 2));
    }

    private int parseMinute(String line) {
        return Integer.parseInt(line.substring(3));
    }

    @Override
    public Iterator<OfferBlank> iterator() {
        return new OffersIterator(this.offerLinks.iterator());
    }

    private class OffersIterator implements Iterator<OfferBlank> {
        private final Iterator<String> offerLinksIterator;

        public OffersIterator(final Iterator<String> offerLinksIterator) {
            this.offerLinksIterator = offerLinksIterator;
        }

        @Override
        public boolean hasNext() {
            return this.offerLinksIterator.hasNext();
        }

        @Override
        public OfferBlank next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration has no more elements");
            }
            try {
                return parseOfferTopic(this.offerLinksIterator.next());
            } catch (ParseException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
