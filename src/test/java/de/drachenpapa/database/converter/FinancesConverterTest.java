package de.drachenpapa.database.converter;

import de.drachenpapa.database.records.FinancesEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FinancesConverterTest {

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldConvertCorrectly() throws SQLException {
        // Given
        given(resultSet.next()).willReturn(true, false);
        given(resultSet.getInt("id")).willReturn(1);
        given(resultSet.getDate("date")).willReturn(Date.valueOf("2024-03-01"));
        given(resultSet.getDouble("amount")).willReturn(100.0);
        given(resultSet.getString("description")).willReturn("Salary");
        given(resultSet.getInt("account_id")).willReturn(1);
        given(resultSet.getInt("category_id")).willReturn(1);

        // When
        List<FinancesEntry> financesEntries = FinancesConverter.convert(resultSet);

        // Then
        assertThat("Finances entry list should have one element", financesEntries, hasSize(1));

        FinancesEntry financesEntry = financesEntries.get(0);
        assertAll("FinancesEntry was not properly converted",
                () -> assertThat("Finances entry id should match", financesEntry.id(), is(1)),
                () -> assertThat("Finances entry date should match", financesEntry.date(), is(LocalDate.of(2024, 3, 1))),
                () -> assertThat("Finances entry amount should match", financesEntry.amount(), is(100.0)),
                () -> assertThat("Finances entry description should match", financesEntry.description(), is("Salary")),
                () -> assertThat("Finances entry account id should match", financesEntry.accountId(), is(1)),
                () -> assertThat("Finances entry category id should match", financesEntry.categoryId(), is(1)));
    }

    @Test
    public void shouldHandleEmptyResultSet() throws SQLException {
        // Given
        given(resultSet.next()).willReturn(false);

        // When
        List<FinancesEntry> financesEntries = FinancesConverter.convert(resultSet);

        // Then
        assertThat("Finances entry list should be empty", financesEntries, hasSize(0));
    }
}
