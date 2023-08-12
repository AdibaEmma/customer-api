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
    public void updateCustomer(Integer id, CustomerUpdateRequest updateRequest) {
        Customer foundCustomer = getCustomer(id);
        if (
                foundCustomer.getName().equals(updateRequest.name())
                && foundCustomer.getEmail().equals(updateRequest.email())
                && foundCustomer.getAge().equals(updateRequest.age())
        ) {
            throw new RequestValidationException("could not perform update");
        }
        foundCustomer.setName(updateRequest.name());
        foundCustomer.setEmail(updateRequest.email());
        foundCustomer.setAge(updateRequest.age());

        customerDao.updateCustomer(foundCustomer);
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
