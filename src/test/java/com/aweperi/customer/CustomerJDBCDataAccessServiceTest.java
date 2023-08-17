package com.aweperi.customer;

import com.aweperi.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {
    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // Given

        // When

        //Then
    }

    @Test
    void selectCustomerById() {
        // Given

        // When

        //Then
    }

    @Test
    void insertCustomer() {
        // Given

        // When

        //Then
    }

    @Test
    void existsPersonWithEmail() {
        // Given

        // When

        //Then
    }

    @Test
    void existsPersonWithId() {
        // Given

        // When

        //Then
    }

    @Test
    void deleteCustomerById() {
        // Given

        // When

        //Then
    }

    @Test
    void updateCustomer() {
        // Given

        // When

        //Then
    }
}