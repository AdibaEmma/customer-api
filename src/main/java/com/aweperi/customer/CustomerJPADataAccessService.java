package com.aweperi.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
@RequiredArgsConstructor
public class CustomerJPADataAccessService implements CustomerDao {
    private final CustomerRepository customerRepository;
    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return customerRepository.existsCustomersById(id);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Customer update) {
        customerRepository.save(update);
    }
}
