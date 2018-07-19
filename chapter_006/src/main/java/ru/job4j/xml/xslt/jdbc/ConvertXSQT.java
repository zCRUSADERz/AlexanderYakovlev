package ru.job4j.xml.xslt.jdbc;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Конвертер XML.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.07.2018
 */
public class ConvertXSQT {

    /**
     * Конвертировать source.xml файл на основании схемы в dest файл.
     * @param source изначальный xml.
     * @param dest результат конвертации.
     * @param scheme схема для конвертации.
     * @throws TransformerException
     */
    public void convert(File source, File dest, File scheme) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(
                new StreamSource(scheme)
        );
        transformer.transform(
                new StreamSource(source), new StreamResult(dest)
        );
    }
}
