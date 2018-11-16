package ru.job4j.xml.xslt.jdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Хранилище записей Entry в виде XML файла.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 19.07.2018
 */
public class StoreXML {
    private final File target;

    public StoreXML(final File target) {
        this.target = target;
    }

    /**
     * Сохранить записи в XML файле.
     * @param entries список записей.
     * @throws JAXBException если произошла ошибка при конвертации записей в XML.
     */
    public void save(Entries entries)throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(entries, target);
    }
}
