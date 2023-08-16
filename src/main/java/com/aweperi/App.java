package com.aweperi;

import com.aweperi.customer.Customer;
import com.aweperi.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            var alex = new Customer(
                    "Alex",
                    "alex@gmail.com",
                    21
            );

            var jamila = new Customer(
                    "Jamila",
                    "jamila@gmail.com",
                    19
            );
            List<Customer> customers = List.of(alex, jamila);

//            customerRepository.saveAll(customers);
        };
    }
}
