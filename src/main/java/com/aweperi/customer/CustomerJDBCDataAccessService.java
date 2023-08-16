package com.aweperi.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
@RequiredArgsConstructor
public class CustomerJDBCDataAccessService implements CustomerDao{
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;

        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper);
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public boolean customerEmailExists(String email) {
        return false;
    }

    @Override
    public boolean customerIdExists(Integer id) {
        return false;
    }

    @Override
    public void removeCustomerById(Integer id) {

    }

    @Override
    public void updateCustomer(Customer update) {

    }
}
