package com.spring.boot.rest.exceptions.customExceptions;

public class TokenExceptions extends RuntimeException {
    public TokenExceptions(String message) {
        super(message);
    }
}
