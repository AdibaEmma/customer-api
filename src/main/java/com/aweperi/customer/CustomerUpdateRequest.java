package com.aweperi.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
        ) {}
