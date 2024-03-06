package de.drachenpapa.database;

public record CategoryEntry(
        int id,
        String name,
        String category,
        boolean income
) {}
