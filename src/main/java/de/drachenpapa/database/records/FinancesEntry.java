package de.drachenpapa.database.records;

import java.time.LocalDate;

/**
 * Represents a financial entry record in the database.
 */
public record FinancesEntry(
        /**
         * The unique identifier of the financial entry.
         */
        int id,
        /**
         * The date of the financial entry.
         */
        LocalDate date,
        /**
         * The amount of the financial entry.
         */
        double amount,
        /**
         * The description of the financial entry.
         */
        String description,
        /**
         * The ID of the account associated with the financial entry.
         */
        int accountId,
        /**
         * The ID of the category associated with the financial entry.
         */
        int categoryId
) {}
