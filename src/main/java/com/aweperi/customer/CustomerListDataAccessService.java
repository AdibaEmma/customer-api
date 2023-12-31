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
                1L,
                "Alex",
                "alex@gmail.com",
                21
        );

        var jamila = new Customer(
                2L,
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
    public Optional<Customer> selectCustomerById(Long id) {
        return mockCustomers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        mockCustomers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return mockCustomers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerWithId(Long id) {
        return mockCustomers.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Long id) {
        mockCustomers = mockCustomers.stream()
                .filter(c -> !c.getId().equals(id))
                .toList();
    }

    @Override
    public void updateCustomer(Customer update) {
        List<Customer> newList = new ArrayList<>(mockCustomers.stream()
                .filter(c -> !c.getId().equals(update.getId()))
                .toList());

        Customer customerToUpdate = mockCustomers.stream()
                .filter(c -> c.getId().equals(update.getId()))
                .findFirst().get();

        customerToUpdate.setName(update.getName());
        customerToUpdate.setEmail(update.getEmail());
        customerToUpdate.setAge(update.getAge());

        newList.add(customerToUpdate);
        mockCustomers = newList;
    }
}
