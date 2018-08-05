package ru.job4j.sqlru;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.sqlru.store.DBException;
import ru.job4j.sqlru.store.SqlRuAppDB;

import java.util.Properties;

/**
 * Парсер вакансий с сайта sql.ru.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuApp {
    private final String propertiesPath;
    private final Logger logger = Logger.getLogger(SqlRuApp.class);

    public SqlRuApp(final String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    /**
     * Запускает Quartz sсheduler на первое выполнение работы
     * и дальнейшие запуски на основании крон выражения.
     * @throws SqlRuException, если возникла ошибка
     * при настройке и запуске приложения.
     */
    public void start() throws SqlRuException {
        this.logger.info("Sql.ru parser running.");
        Properties properties;
        try {
            properties = new Config(this.propertiesPath).getConfig();
            new SqlRuAppDB(properties).initDB();
        } catch (DBException | ConfigException e) {
            this.logger.fatal("Database initialization error.", e);
            throw new SqlRuException("Database initialization error.", e);
        }
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            JobDetail firstRunParser = JobBuilder.newJob(SqlRuJob.class)
                    .withIdentity("first_run", "group1")
                    .build();
            JobDataMap data = firstRunParser.getJobDataMap();
            data.put("properties_path", this.propertiesPath);
            data.put("first_run", true);
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("first_run_trigger", "group1")
                    .startNow()
                    .build();
            scheduler.scheduleJob(firstRunParser, trigger);
            JobDetail parser = JobBuilder.newJob(SqlRuJob.class)
                    .withIdentity("parser", "group1")
                    .build();
            data = parser.getJobDataMap();
            data.put("properties_path", this.propertiesPath);
            data.put("first_run", true);
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("parser_trigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(
                            properties.getProperty("cron.time"))
                    ).startNow()
                    .build();
            scheduler.scheduleJob(parser, trigger);
        } catch (SchedulerException e) {
            this.logger.fatal("Scheduler error.", e);
            throw new SqlRuException("Scheduler error.", e);
        }
    }

    public static void main(String[] args) throws SqlRuException {
        new SqlRuApp(args[0]).start();
    }
}
