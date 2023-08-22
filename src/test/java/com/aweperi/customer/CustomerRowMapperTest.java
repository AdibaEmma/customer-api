package com.aweperi.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSetMock = mock(ResultSet.class);
        when( resultSetMock.getLong("id")).thenReturn(1L);
        when( resultSetMock.getString("name")).thenReturn("Alex");
        when( resultSetMock.getString("email")).thenReturn("alex@gmail.com");
        when(resultSetMock.getInt("age")).thenReturn(19);
        // When
        Customer actual = customerRowMapper.mapRow(resultSetMock, 1);
        //Then
        Customer expected = new Customer(1L, "Alex", "alex@gmail.com", 19);
        assertThat(actual).isEqualTo(expected);
    }
}