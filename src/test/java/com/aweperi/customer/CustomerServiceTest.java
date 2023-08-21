package com.aweperi.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    private CustomerService underTest;
    @Mock
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        // Given

        // When

        //Then
    }

    @Test
    void getCustomer() {
        // Given

        // When

        //Then
    }

    @Test
    void addCustomer() {
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

    @Test
    void deleteCustomer() {
        // Given

        // When

        //Then
    }
}