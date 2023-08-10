package com.aweperi.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.returnAllCustomers();
    }

    @Override
    public Customer getCustomer(Integer id) {
        return customerDao.returnCustomerById(id)
                .orElseThrow(() ->
                new IllegalArgumentException(
                        "Customer with id [%s] not found".formatted(id)));
    }
}
