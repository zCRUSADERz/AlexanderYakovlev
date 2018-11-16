package ru.job4j.sqlru;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.sqlru.store.DBException;
import ru.job4j.sqlru.store.SqlRuAppDB;
import ru.job4j.sqlru.utils.Config;
import ru.job4j.sqlru.utils.ConfigException;

/**
 * Парсер вакансий с сайта sql.ru.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class SqlRuApp {
    private final Config config;
    private final Logger logger = Logger.getLogger(SqlRuApp.class);

    public SqlRuApp(final Config config) {
        this.config = config;
    }

    /**
     * Запускает Quartz scheduler на первое выполнение работы
     * и дальнейшие запуски на основании крон выражения.
     * @throws SqlRuException, если возникла ошибка
     * при настройке и запуске приложения.
     */
    public void start() throws SqlRuException {
        this.logger.info("Sql.ru parser running.");
        try {
            new SqlRuAppDB(this.config).initDB();
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            JobDetail firstRunParser = JobBuilder.newJob(SqlRuJob.class)
                    .withIdentity("first_run", "group1")
                    .build();
            JobDataMap data = firstRunParser.getJobDataMap();
            data.put("config", this.config);
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
            data.put("config", this.config);
            data.put("first_run", true);
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("parser_trigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(
                            this.config.getConfig().getProperty("cron.time"))
                    ).startNow()
                    .build();
            scheduler.scheduleJob(parser, trigger);
        } catch (DBException | ConfigException e) {
            throw new SqlRuException("Database initialization error.", e);
        } catch (SchedulerException e) {
            throw new SqlRuException("Scheduler error.", e);
        }
    }

    public static void main(String[] args) throws SqlRuException {
        new SqlRuApp(new Config(args[0])).start();
    }
}
