package com.aweperi.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/v1/customers")
    List<Customer> getMockCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/api/v1/customers/{id}")
    Customer  getMockCustomer(@PathVariable("id") Integer id) {
        return customerService.getCustomer(id);
    }
}
