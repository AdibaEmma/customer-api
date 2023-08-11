package com.aweperi.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    List<Customer> getMockCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    Customer  getMockCustomer(@PathVariable("id") Integer id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
    }

    @RequestMapping(
            value = "/{id}",
            method={RequestMethod.DELETE, RequestMethod.GET}
    )
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
    }
}
