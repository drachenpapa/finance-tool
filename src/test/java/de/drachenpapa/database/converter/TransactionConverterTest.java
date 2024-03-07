package de.drachenpapa.database.converter;

import de.drachenpapa.database.records.Transaction;
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
public class TransactionConverterTest {

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
        List<Transaction> transactions = TransactionConverter.convert(resultSet);

        // Then
        assertThat("Transactions list should have one element", transactions, hasSize(1));

        Transaction transaction = transactions.get(0);
        assertAll("Transaction was not properly converted",
                () -> assertThat("ID should match", transaction.id(), is(1)),
                () -> assertThat("Date should match", transaction.date(), is(LocalDate.of(2024, 3, 1))),
                () -> assertThat("Amount should match", transaction.amount(), is(100.0)),
                () -> assertThat("Description should match", transaction.description(), is("Salary")),
                () -> assertThat("Account id should match", transaction.accountId(), is(1)),
                () -> assertThat("Category id should match", transaction.categoryId(), is(1)));
    }

    @Test
    public void shouldHandleEmptyResultSet() throws SQLException {
        // Given
        given(resultSet.next()).willReturn(false);

        // When
        List<Transaction> transactions = TransactionConverter.convert(resultSet);

        // Then
        assertThat("Transaction list should be empty", transactions, hasSize(0));
    }
}
