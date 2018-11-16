package ru.job4j.sqlru.utils;

import org.apache.log4j.Logger;

import java.util.Date;

public class SimpleDate {
    private final Date date;
    private final Logger logger = Logger.getLogger(SimpleDate.class);

    public SimpleDate(final Date date) {
        this.date = date;
    }

    public boolean isLaterThan(Date otherDate, String msg) {
        boolean result = this.date.compareTo(otherDate) > 0;
        if (!result) {
            this.logger.info(String.format(
                    "%s. %s - раньше чем: %s.",
                    msg, this.date, otherDate
                    )
            );
        }
        return result;
    }

    public java.sql.Date sqlDate() {
        return new java.sql.Date(this.date.getTime());
    }
}
