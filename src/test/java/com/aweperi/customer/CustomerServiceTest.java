package com.aweperi.customer;

import com.aweperi.exception.DuplicateResourceException;
import com.aweperi.exception.RequestValidationException;
import com.aweperi.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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
    void canGetCustomer() {
        // Given
        Long id = 1L;
        var customer = Customer.builder()
                        .id(1L)
                        .name("Alex")
                        .email("alex@gmail.com")
                        .age(22)
                        .build();

        when(customerDao.selectCustomerById(id)).thenReturn( Optional.of(customer));

        // When
        Customer actual = underTest.getCustomer(id);

        //Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowResourceNotFoundExceptionWhenGetCustomerReturnEmptyOptional() {
        // Given
        Long id = 10L;

        when(customerDao.selectCustomerById(id)).thenReturn( Optional.empty());

        // When
        //Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "customer with id [%s] not found".formatted(id)
                );
    }

    @Test
    void addCustomer() {
        // Given
        String email = "alex@gmail.com";

        when(customerDao.existsCustomerWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex", email, 19
        );

        // When
        underTest.addCustomer(request);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowDuplicateResourceExceptionWhenEmailExistsForAddCustomer() {
        // Given
        String email = "alex@gmail.com";

        when(customerDao.existsCustomerWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex", email, 19
        );

        // When
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage(
                        "email already taken"
                );

        // Then
        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void canUpdateAllCustomerProperties() {
        // Given
        Long id = 1L;
        var customer = Customer.builder()
                .id(1L)
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .build();

        String newEmail = "alexandro@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", newEmail, 22
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(id, updateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canUpdateCustomerNameProperty() {
        // Given
        Long id = 1L;
        var customer = Customer.builder()
                .id(1L)
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .build();

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", null, null
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // When
        underTest.updateCustomer(id, updateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateCustomerEmailProperty() {
        // Given
        Long id = 1L;
        var customer = Customer.builder()
                .id(1L)
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .build();

        String newEmail = "alexandro@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(id, updateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateCustomerAgeProperty() {
        // Given
        Long id = 1L;
        var customer = Customer.builder()
                .id(1L)
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .build();

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, null, 22
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // When
        underTest.updateCustomer(id, updateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void willThrowDuplicateResourceExceptionWhenEmailExists() {
        // Given
        Long id = 1L;
        var customer = Customer.builder()
                .id(1L)
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .build();

        String newEmail = "alexandro@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", newEmail, 22
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        //Then
        verify(customerDao, never()).updateCustomer(any());

    }

    @Test
    void willThrowRequestValidationExceptionWhenNoChanges() {
        // Given
        Long id = 1L;
        var customer = Customer.builder()
                .id(1L)
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .build();

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, null, null
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("could not perform update");

        //Then
        verify(customerDao, never()).updateCustomer(any());

    }

    @Test
    void deleteCustomer() {
        // Given
        Long id = 1L;
        when(customerDao.existsCustomerWithId(id)).thenReturn(true);

        // When
        underTest.deleteCustomer(id);

        //Then
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void willThrowResourceNotFoundExceptionWhenIdNotFoundInDeleteCustomer() {
        // Given
        Long id = 1L;
        when(customerDao.existsCustomerWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));

        //Then
        verify(customerDao, never()).deleteCustomerById(id);
    }
}