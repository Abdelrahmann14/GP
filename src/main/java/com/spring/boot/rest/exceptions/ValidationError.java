package com.spring.boot.rest.exceptions;

public record ValidationError(String field,String message) {
}
