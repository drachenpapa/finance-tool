package de.drachenpapa.database.converter;

import de.drachenpapa.database.records.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts a ResultSet to a list of {@link Account}.
 */
public class AccountConverter {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String IBAN_COLUMN = "iban";


    /**
     * Converts a ResultSet to a list of {@link Account}.
     *
     * @param resultSet The ResultSet containing account data.
     * @return A list of {@link Account}.
     * @throws SQLException If a database access error occurs.
     */
    public static List<Account> convert(ResultSet resultSet) throws SQLException {
        List<Account> accountList = new ArrayList<>();
        if (resultSet == null) {
            return accountList;
        }

        try (resultSet) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID_COLUMN);
                String name = resultSet.getString(NAME_COLUMN);
                String iban = resultSet.getString(IBAN_COLUMN);

                Account account = new Account(id, name, iban);
                accountList.add(account);
            }
        }

        return accountList;
    }
}
