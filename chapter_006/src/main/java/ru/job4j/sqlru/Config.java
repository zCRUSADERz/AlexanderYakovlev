package ru.job4j.sqlru;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Properties configuration.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
public class Config {
    private final String propertiesPath;
    private final Logger logger = Logger.getLogger(Config.class);

    public Config(final String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    /**
     * @return Properties from file.
     * @throws ConfigException, properties file not found.
     */
    public Properties getConfig() throws ConfigException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(this.propertiesPath));
        } catch (IOException e) {
            this.logger.fatal("File with properties not found.", e);
            throw new ConfigException("File with properties not found.", e);
        }
        return properties;
    }
}
