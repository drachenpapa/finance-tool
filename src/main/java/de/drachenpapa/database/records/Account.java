package de.drachenpapa.database.records;

/**
 * Represents an account record in the database.
 */
public record Account(
        /**
         * The unique identifier of the account.
         */
        int id,
        /**
         * The name of the account.
         */
        String name,
        /**
         * The IBAN (International Bank Account Number) associated with the account.
         */
        String iban
) {}
