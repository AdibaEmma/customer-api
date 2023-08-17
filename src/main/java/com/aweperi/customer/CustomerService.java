package com.aweperi.customer;

import com.aweperi.exception.RequestValidationException;
import com.aweperi.exception.DuplicateResourceException;
import com.aweperi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)));
    }

    @Override
    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String customerEmail = customerRegistrationRequest.email();
        if (customerDao.existsPersonWithEmail(customerEmail)) {
            throw new DuplicateResourceException("email already taken");
        }

        customerDao.insertCustomer(
                Customer.builder()
                        .name(customerRegistrationRequest.name())
                        .email(customerEmail)
                        .age(customerRegistrationRequest.age())
                        .build()
        );
    }

    @Override
    public void updateCustomer(Long id, CustomerUpdateRequest updateRequest) {
        Customer foundCustomer = getCustomer(id);
        boolean changes = false;

        if (updateRequest.name() != null && !foundCustomer.getName().equals(updateRequest.name())) {
            foundCustomer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.email() != null && !foundCustomer.getEmail().equals(updateRequest.email())) {
            if (customerDao.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException("email already taken");
            }
            foundCustomer.setEmail(updateRequest.email());
            changes = true;
        }

        if (updateRequest.age() != null && !foundCustomer.getAge().equals(updateRequest.age())) {
            foundCustomer.setAge(updateRequest.age());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("could not perform update");
        }

        customerDao.updateCustomer(foundCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerDao.existsPersonWithId(id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(id));
        }
        customerDao.deleteCustomerById(id);
    }
}
