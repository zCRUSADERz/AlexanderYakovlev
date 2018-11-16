package ru.job4j.sqlru;

import org.apache.log4j.Logger;
import org.quartz.*;
import ru.job4j.sqlru.offers.*;
import ru.job4j.sqlru.parsers.SqlRuTopicParser;
import ru.job4j.sqlru.parsers.filters.TopicsUpdatedNotEarlierThan;
import ru.job4j.sqlru.store.SimpleOfferStore;
import ru.job4j.sqlru.store.SqlRuAppDB;
import ru.job4j.sqlru.offers.filters.OffersCreatedNotEarlierThan;
import ru.job4j.sqlru.offers.filters.OffersNotExistingInStore;
import ru.job4j.sqlru.offers.filters.SpecificOffers;
import ru.job4j.sqlru.parsers.SqlRuOffersParser;
import ru.job4j.sqlru.store.OfferStore;
import ru.job4j.sqlru.utils.Config;

import java.util.*;

/**
 * Задача сбора Java вакансий.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuJob implements Job {
    private final Logger logger = Logger.getLogger(SqlRuJob.class);

    @Override
    public void execute(
            JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.logger.info(String.format("SqlRuJob run. %s", new Date()));
        JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
        Date createdNotEarlierThan = getSearchDate(data);
        try (OfferStore store = new SimpleOfferStore(
                new SqlRuAppDB((Config) data.get("config")).getConnection())) {
            new OffersSaveToStore<>(
                    new SpecificOffers<>(
                            new OffersNotExistingInStore(
                                    new OffersCreatedNotEarlierThan(
                                            new SqlRuOffersParser(
                                                    new TopicsUpdatedNotEarlierThan(
                                                            new SqlRuTopicParser(),
                                                            createdNotEarlierThan
                                                    )
                                            ), createdNotEarlierThan
                                    ), store
                            ), new JavaOffers()
                    ), store
            ).save();
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
        this.logger.info(String.format("SqlRuJob finished. %s", new Date()));
    }

    /**
     * Определяет дату не позже которой будут собираться вакансии.
     * При первом запуске будут собраны вакансии за прошлый год.
     * При последующих запусках будут собраны новые вакансии,
     * созданные после прошлого запуска.
     * @param data current Job .
     * @return created not earlier than.
     */
    private Date getSearchDate(JobDataMap data) {
        Date result;
        if (data.getBoolean("first_run")) {
            GregorianCalendar yearAgo = new GregorianCalendar();
            yearAgo.roll(GregorianCalendar.YEAR, false);
            result = yearAgo.getTime();
        } else {
            result = new Date(data.getLong("past_launch"));
        }
        return result;
    }
}
