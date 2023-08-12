package com.aweperi.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
    void insertCustomer(Customer customer);
    boolean customerEmailExists(String email);
    boolean customerIdExists(Integer id);
    void removeCustomerById(Integer id);
    void updateCustomer(Customer update);
}
