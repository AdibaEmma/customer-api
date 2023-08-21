package com.aweperi.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

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
        // When
        underTest.getAllCustomers();
        //Then
        verify(customerDao).selectAllCustomers();
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