package com.aweperi.customer;

import com.aweperi.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + UUID.randomUUID(),
                FAKER.number().numberBetween(16, 99)
        );

        underTest.insertCustomer(customer);

        // When
        List<Customer> actual = underTest.selectAllCustomers();

        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(16, 99)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenIdNotValidInSelectCustomerById() {
        // Given
        Long id = -1L;

        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        var customer = Customer.builder()
            .name(FAKER.name().fullName())
            .email(email)
            .age(21)
            .build();

        // When
        underTest.insertCustomer(customer);
        Optional<Customer> actual = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willThrowDuplicateKeyExceptionWhenDuplicateEmailInInsertCustomer() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        var customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(21)
                .build();

        //Then
       assertThrows(DuplicateKeyException.class, () -> {
           underTest.insertCustomer(customer);
           underTest.insertCustomer(customer);
       });
    }

    @Test
    void existsPersonWithEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        var customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(21)
                .build();

        underTest.insertCustomer(customer);

        // When
        boolean actual = underTest.existsPersonWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithId() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(16, 99)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        boolean actual = underTest.existsPersonWithId(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void deleteCustomerById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.number().numberBetween(16, 99)
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        underTest.deleteCustomerById(id);
        boolean actual = underTest.existsPersonWithId(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void updateCustomer() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                22
        );

        underTest.insertCustomer(customer);

        Customer actual = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElseThrow();

        var expected = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(21)
                .build();

        // When
        underTest.updateCustomer(expected);

        //Then
        assertThat(expected.getName()).isNotEqualTo(actual.getName());
        assertThat(expected.getAge()).isNotEqualTo(actual.getAge());
    }
}