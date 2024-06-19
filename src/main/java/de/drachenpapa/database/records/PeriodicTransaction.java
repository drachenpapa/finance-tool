package de.drachenpapa.database.records;

import java.time.LocalDate;

public record PeriodicTransaction (
        // Index
        int id,
        // Erste Ausführung
        LocalDate firstExecution,
        // Letzte Ausführung -> null = unbegrenzt
        LocalDate finalExecution,
        // wann zuletzt ausgeführt wurde
        LocalDate lastExecution,
        // Wert, ggf. Currency.class
        double amount,
        // Beschreibung
        String description,
        // Referenz zu welchem Account
        int accountId,
        // Referenz zur Kategorie
        int categoryId
) {}
