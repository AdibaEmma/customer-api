package com.aweperi.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CustomerJPADataAccessServiceTest {
    private CustomerJPADataAccessService underTest;
    @Mock
    private CustomerRepository customerRepository;
    private AutoCloseable autoCloseable;
    @BeforeEach
    void setUp() {
         autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
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
    void existsCustomerWithEmail() {
        // Given

        // When

        //Then
    }

    @Test
    void existsCustomerWithId() {
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