package ru.job4j.sqlru.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateParser {
    private final String dateLine;

    public DateParser(final String dateLine) {
        this.dateLine = dateLine;
    }

    /**
     * Преобразует строку с датой в объект Date.
     * Преобразует нестандартные даты: "сегодня" и "вчера" к стандартноу виду.
     * @return parsed Date.
     */
    public Date parse() {
        Date result;
        String line;
        if (this.dateLine.contains("сегодня")) {
            line = this.dateLine.replace("сегодня, ", "");
            Calendar today = parseUnformattedDate(line);
            result = today.getTime();
        } else if (this.dateLine.contains("вчера")) {
            line = this.dateLine.replace("вчера, ", "");
            Calendar today = parseUnformattedDate(line);
            today.roll(Calendar.DAY_OF_MONTH, false);
            result = today.getTime();
        } else {
            String pattern = "d MMM yy, HH:mm";
            try {
                result = new SimpleDateFormat(
                        pattern,
                        new Locale("ru")
                ).parse(this.dateLine);
            } catch (ParseException e) {
                throw new IllegalStateException(
                        String.format(
                                "Wrong date format, parse error.%nPattern: %s, dateString: %s.",
                                pattern, this.dateLine
                        ), e
                );
            }
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
}
