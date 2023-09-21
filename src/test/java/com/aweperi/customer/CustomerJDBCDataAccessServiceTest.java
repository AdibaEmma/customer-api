package com.aweperi.customer;

import com.aweperi.AbstractTestcontainers;
import org.jetbrains.annotations.NotNull;
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
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = insertAndReturnCustomer(email);

        // When
        List<Customer> actual = underTest.selectAllCustomers();

        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = insertAndReturnCustomer(email);

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
    void existsCustomerWithEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        var customer = insertAndReturnCustomer(email);

        // When
        boolean actual = underTest.existsCustomerWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerWithEmailReturnsFalseWhenDoesNotExists() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();

        // When
        boolean actual = underTest.existsCustomerWithEmail(email);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerWithId() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = insertAndReturnCustomer(email);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        boolean actual = underTest.existsCustomerWithId(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerWithIdReturnsFalseWhenDoesNotExists() {
        // Given
        Long id = -1L;

        // When
        boolean actual = underTest.existsCustomerWithId(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = insertAndReturnCustomer(email);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        underTest.deleteCustomerById(id);

        //
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateCustomerName() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = insertAndReturnCustomer(email);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        String newName =FAKER.name().fullName();
        var update = Customer.builder()
                .id(id)
                .name(newName)
                .email(email)
                .build();

        // When
        underTest.updateCustomer(update);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(expected -> {
            assertThat(expected.getId()).isEqualTo(id);
            assertThat(expected.getName()).isEqualTo(newName);
            assertThat(expected.getEmail()).isEqualTo(customer.getEmail());
            assertThat(expected.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void updateCustomerAge() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = insertAndReturnCustomer(email);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        int newAge = 21;
        var update = Customer.builder()
                .id(id)
                .age(newAge)
                .build();

        // When
        underTest.updateCustomer(update);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(expected -> {
            assertThat(expected.getId()).isEqualTo(id);
            assertThat(expected.getName()).isEqualTo(customer.getName());
            assertThat(expected.getEmail()).isEqualTo(customer.getEmail());
            assertThat(expected.getAge()).isEqualTo(newAge);
        });
    }

//    @Test
//    void updateCustomerEmail() {
//        // Given
//        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
//        Customer customer = insertAndReturnCustomer(email);
//
//        Long id = underTest.selectAllCustomers().stream()
//                .filter(c -> c.getEmail().equals(email))
//                .map(Customer::getId)
//                .findFirst()
//                .orElseThrow();
//
//        String newEmail = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
//        var update = Customer.builder()
//                .id(id)
//                .email(newEmail)
//                .build();
//
//        // When
//        underTest.updateCustomer(update);
//
//        //Then
//        Optional<Customer> actual = underTest.selectCustomerById(id);
//        assertThat(actual).isPresent().hasValueSatisfying(expected -> {
//            assertThat(expected.getId()).isEqualTo(id);
//            assertThat(expected.getName()).isEqualTo(customer.getName());
//            assertThat(expected.getEmail()).isEqualTo(newEmail);
//            assertThat(expected.getAge()).isEqualTo(customer.getAge());
//        });
//    }

    @Test
    void willUpdateAllCustomerProperties() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = insertAndReturnCustomer(email);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var expected = Customer.builder()
                .id(id)
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + UUID.randomUUID())
                .age(21)
                .build();

        // When
        underTest.updateCustomer(expected);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).hasValue(expected);
    }

    @Test
    void willNotUpdateCustomerWhenNothingToUpdate() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer = insertAndReturnCustomer(email);

        Long id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var update = Customer.builder()
                .id(id)
                .build();

        // When
        underTest.updateCustomer(update);

        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(expected -> {
            assertThat(expected.getId()).isEqualTo(id);
            assertThat(expected.getName()).isEqualTo(customer.getName());
            assertThat(expected.getEmail()).isEqualTo(customer.getEmail());
            assertThat(expected.getAge()).isEqualTo(customer.getAge());
        });
    }

    @NotNull
    private Customer insertAndReturnCustomer(String email) {
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                22
        );

        underTest.insertCustomer(customer);
        return customer;
    }
}