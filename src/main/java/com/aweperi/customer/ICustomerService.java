package com.aweperi.customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomer(Integer id);

    void addCustomer(CustomerRegistrationRequest customerRegistrationRequest);
    void updateCustomer(Integer id, CustomerUpdateRequest updateRequest);
    void deleteCustomer(Integer id);
}
