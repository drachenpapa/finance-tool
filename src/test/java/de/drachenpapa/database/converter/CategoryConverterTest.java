package de.drachenpapa.database.converter;

import de.drachenpapa.database.records.Category;
import de.drachenpapa.database.records.CategoryType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CategoryConverterTest {

    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldConvertCorrectly() throws SQLException {
        // Given
        given(resultSet.next()).willReturn(true, false);
        given(resultSet.getInt("id")).willReturn(1);
        given(resultSet.getString("name")).willReturn("Groceries");
        given(resultSet.getString("category")).willReturn("Food");
        given(resultSet.getBoolean("income")).willReturn(false);

        // When
        List<Category> categories = CategoryConverter.convert(resultSet);

        // Then
        assertThat("Category list should have one element", categories, hasSize(1));

        Category category = categories.get(0);
        assertAll("Category was not properly converted",
                () -> assertThat("ID should match", category.id(), is(1)),
                () -> assertThat("Name should match", category.name(), is("Groceries")),
                () -> assertThat("Category should match", category.category(), is("Food")),
                () -> assertThat("CategoryType should match", category.categoryType(), is(CategoryType.EXPENSE)));
    }

    @Test
    public void shouldHandleEmptyResultSet() throws SQLException {
        // Given
        given(resultSet.next()).willReturn(false);

        // When
        List<Category> categories = CategoryConverter.convert(resultSet);

        // Then
        assertThat("Category list should not be null", categories, isNotNull());
        assertThat("Category list should be empty", categories, hasSize(0));
    }
}
