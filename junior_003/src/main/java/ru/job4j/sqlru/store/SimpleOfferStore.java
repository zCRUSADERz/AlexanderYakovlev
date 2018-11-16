package ru.job4j.sqlru.store;

import net.jcip.annotations.NotThreadSafe;
import org.apache.log4j.Logger;
import ru.job4j.sqlru.utils.SimpleDate;
import ru.job4j.sqlru.offers.JavaOffers;

import java.sql.*;

/**
 * Java offer store.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 04.08.2018
 */
@NotThreadSafe
public class SimpleOfferStore implements OfferStore {
    private final Connection connection;
    private final Logger logger = Logger.getLogger(JavaOffers.class);

    public SimpleOfferStore(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Find offer by offer id.
     * @param id offer id.
     * @return true, if found.
     * @throws DBException if throw SQLException.
     */
    public boolean findById(int id) throws DBException {
        String query = "SELECT * FROM offers WHERE id = ?;";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DBException("Find by Id error.", e);
        }
    }

    /**
     * Save offer to store.
     * @param id offer id.
     * @param title offer title.
     * @param text offer description.
     * @param created offer created date.
     * @param url url to offer.
     * @throws DBException if throw SQLException.
     */
    public void save(int id, String title, String text,
                     SimpleDate created, String url) throws DBException {
        String insertQuery =
                "INSERT INTO offers (id, title, description, created, url)"
                + " VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement
                     = this.connection.prepareStatement(insertQuery)) {
            statement.setInt(1, id);
            statement.setString(2, title);
            statement.setString(3, text);
            statement.setDate(4, created.sqlDate());
            statement.setString(5, url);
            statement.execute();
            this.logger.info(String.format(
                    "Вакансия с id: %d - Добавлена в БД.", id
                    )
            );
        } catch (SQLException e) {
            throw new DBException("Insert error.", e);
        }
    }

    @Override
    public void close() throws DBException {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new DBException("Close connection error.", e);
        }
    }
}
