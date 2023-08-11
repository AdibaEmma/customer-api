package com.aweperi.customer;

public record CustomerRegistrationRequest (
    String name,
    String email,
    Integer age
) {}
