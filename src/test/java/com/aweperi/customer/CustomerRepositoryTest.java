package com.aweperi.customer;

import com.aweperi.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {
    private CustomerRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void existsCustomerByEmail() {
        // Given

        // When

        //Then
    }

    @Test
    void existsCustomersById() {
        // Given

        // When

        //Then
    }
}