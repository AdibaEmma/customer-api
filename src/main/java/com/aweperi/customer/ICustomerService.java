package com.aweperi.customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomer(Long id);

    void addCustomer(CustomerRegistrationRequest customerRegistrationRequest);
    void updateCustomer(Long id, CustomerUpdateRequest updateRequest);
    void deleteCustomer(Long id);
}
