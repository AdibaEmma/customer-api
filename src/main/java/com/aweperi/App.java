package com.aweperi;

import com.aweperi.customer.Customer;
import com.aweperi.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            var faker = new Faker();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            var customer = new Customer(
                   String.format("%s %s", firstName, lastName),
                   String.format(
                           "%s.%s@aweperi.com",
                           firstName.toLowerCase(),
                           lastName.toLowerCase()
                   ),
                    faker.number().numberBetween(16, 99)
            );

            customerRepository.save(customer);
        };
    }
}
