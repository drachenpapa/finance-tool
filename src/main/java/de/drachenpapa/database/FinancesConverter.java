package de.drachenpapa.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancesConverter {
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String AMOUNT = "amount";
    private static final String DESCRIPTION = "description";
    private static final String ACCOUNT_ID = "account_id";
    private static final String CATEGORY_ID = "category_id";

    public static List<FinancesEntry> convertResultSetToFinancesEntries(ResultSet resultSet) throws SQLException {
        List<FinancesEntry> financesEntries = new ArrayList<>();
        if (resultSet == null) {
            return financesEntries;
        }
        try (resultSet) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                LocalDate date = resultSet.getDate(DATE).toLocalDate();
                double amount = resultSet.getDouble(AMOUNT);
                String description = resultSet.getString(DESCRIPTION);
                int accountId = resultSet.getInt(ACCOUNT_ID);
                int categoryId = resultSet.getInt(CATEGORY_ID);

                FinancesEntry financesEntry = new FinancesEntry(id, date, amount, description, accountId, categoryId);
                financesEntries.add(financesEntry);
            }
        }
        return financesEntries;
    }
}
