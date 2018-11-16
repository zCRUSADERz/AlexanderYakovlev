package ru.job4j.xml.xslt.jdbc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Сумматор значений аттрибутов из XML файла.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.07.2018
 */
public class SumXMLAttr {
    private final File source;
    private long sum;

    public SumXMLAttr(final File source) {
        this.source = source;
    }

    /**
     * Печатает сумму значений аттрибутов из XML файла
     * @throws ParserConfigurationException ошибка конфигурации парсера.
     * @throws SAXException ошибка при парсинге файла.
     * @throws IOException ошибка ввода-вывода.
     */
    public void printSum() throws ParserConfigurationException, SAXException, IOException {
        this.sum = 0;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser parser = saxParserFactory.newSAXParser();
        parser.parse(
                this.source,
                new DefaultHandler() {
                    @Override
                    public void startElement(String uri, String localName,
                                             String qName, Attributes attributes) {
                        if (attributes.getLength() != 0) {
                            sum += Long.parseLong(attributes.getValue(0));
                        }
                    }
                });
        System.out.println("Сумма: " + this.sum);
    }
}
