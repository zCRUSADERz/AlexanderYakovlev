package ru.job4j.xml.xslt.jdbc;

import java.io.File;
import java.util.Properties;

/**
 * Тестовое задание от фирмы "МАГНИТ".
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.07.2018
 */
public class App {
    private final Properties config;

    public App(final Properties config) {
        this.config = config;
    }

    /**
     * Запуск приложения.
     * @throws Exception если в каком либо из методов было выброшено другое
     * исключение.
     */
    public void start() throws Exception {
        long start = System.currentTimeMillis();
        StoreSQL storeSQL = new StoreSQL(this.config);
        storeSQL.generate(
                Integer.parseInt(
                        this.config.getProperty("num_of_entry")
                )
        );
        new StoreXML(new File(this.config.getProperty("source_xml")))
                .save(storeSQL.allEntry());
        File target = new File(this.config.getProperty("target_xml"));
        new ConvertXSQT().convert(
                new File(this.config.getProperty("source_xml")),
                target,
                new File(this.config.getProperty("schema_xsl"))
        );
        new SumXMLAttr(target).printSum();
        System.out.println(String.format(
                "Время выполнения: %d",
                System.currentTimeMillis() - start)
        );
    }

    public static void main(String[] args) throws Exception {
        new App(createProperties()).start();
    }

    private static Properties createProperties() {
        Properties prop = new Properties();
        prop.setProperty("num_of_entry", "1000000");
        prop.setProperty("jdbc.url", "jdbc:sqlite:xml_xslt_Optimization");
        prop.setProperty("jdbc.table_name", "entry");
        prop.setProperty(
                "source_xml",
                App.class.getResource("/xml/xslt/jdbc/source.xml").getPath()
        );
        prop.setProperty(
                "target_xml",
                App.class.getResource("/xml/xslt/jdbc/target.xml").getPath()
        );
        prop.setProperty(
                "schema_xsl",
                App.class.getResource("/xml/xslt/jdbc/xslt.xsl").getPath()
        );
        return prop;
    }
}
