package com.aweperi.customer;

import com.aweperi.exception.DuplicateResourceException;
import com.aweperi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    @Override
    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)));
    }

    @Override
    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String customerEmail = customerRegistrationRequest.email();
        if (customerDao.customerEmailExists(customerEmail)) {
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
    public void deleteCustomer(Integer id) {
        if (!customerDao.customerIdExists(id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(id));
        }
        customerDao.removeCustomerById(id);
    }
}
