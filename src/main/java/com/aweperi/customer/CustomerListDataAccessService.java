package com.aweperi.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {
    private static List<Customer> mockCustomers;

    static  {
        mockCustomers = new ArrayList<>();
        var alex = new Customer(
                1,
                "Alex",
                "alex@gmail.com",
                21
        );

        var jamila = new Customer(
                2,
                "Jamila",
                "jamila@gmail.com",
                19
        );

        mockCustomers.add(alex);
        mockCustomers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers() {
         return mockCustomers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return mockCustomers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        mockCustomers.add(customer);
    }

    @Override
    public boolean customerEmailExists(String email) {
        return mockCustomers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }
}
