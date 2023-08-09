package com.aweperi.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> returnAllCustomers();
    Optional<Customer>  returnCustomerById(Integer id);
}
