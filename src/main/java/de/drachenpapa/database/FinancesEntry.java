package de.drachenpapa.database;

import java.time.LocalDate;

public record FinancesEntry(
        int id,
        LocalDate date,
        double amount,
        String description,
        int accountId,
        int categoryId
) {}
