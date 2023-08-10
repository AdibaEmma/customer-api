package com.aweperi.exception;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound() {
        System.out.println("Resource not found");
    }

    public ResourceNotFound(String message) {
        super(message);
    }
}
