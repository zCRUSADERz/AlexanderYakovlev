package ru.job4j.sqlru.parsers;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.job4j.sqlru.offers.Offer;
import ru.job4j.sqlru.utils.SimpleDate;

import java.io.IOException;
import java.util.Date;

public class OfferTopic {
    private final String url;
    private final SimpleDate updated;
    private final Logger logger = Logger.getLogger(OfferTopic.class);

    public OfferTopic(final String url, final SimpleDate updated) {
        this.url = url;
        this.updated = updated;
    }

    public boolean isUpdatedLaterThan(Date date) {
        return this.updated.isLaterThan(
                date,
                "Тема последний раз была обновлена позже допустимой даты."
        );
    }

    public Offer parse() throws ParseException {
        try {
            Document doc = Jsoup.connect(this.url).get();
            Element message = doc.selectFirst("table.msgTable");
            int id = Integer.parseInt(
                    message.selectFirst("td.msgFooter > a").ownText()
            );
            String title = message.selectFirst("td.messageHeader").ownText();
            String text = message.selectFirst("td.msgBody + td.msgBody").html();
            String createdLine = message.selectFirst("td.msgFooter").ownText();
            createdLine = createdLine
                    .substring(0, createdLine.indexOf("["))
                    .trim();
            SimpleDate created = new SimpleDate(
                    new DateParser(createdLine).parse()
            );
            return new Offer(id, title, text, created, url);
        } catch (IOException e) {
            this.logger.fatal(String.format("Failed load site: %s", url), e);
            throw new ParseException(
                    String.format("Failed load site: %s", url), e
            );
        }
    }
}
