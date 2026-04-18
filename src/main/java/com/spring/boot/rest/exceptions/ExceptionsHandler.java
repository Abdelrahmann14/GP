package com.spring.boot.rest.exceptions;
import com.spring.boot.rest.exceptions.customExceptions.IdException;
import com.spring.boot.rest.exceptions.customExceptions.IdNullException;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.exceptions.customExceptions.TokenExceptions;
import com.spring.boot.rest.service.bundle.BundleTranslatorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ValidationError(
                        err.getField(),
                        BundleTranslatorService.getMessage(err.getDefaultMessage()).getBundleMessage()))
                .toList();

        return buildErrorResponse(request, errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintValidation(ConstraintViolationException ex, HttpServletRequest request) {
        List<ValidationError> errors = ex.getConstraintViolations().stream()
                .map(v -> new ValidationError(
                        v.getPropertyPath().toString().substring(v.getPropertyPath().toString().lastIndexOf('.') + 1),
                        BundleTranslatorService.getMessage(v.getMessage()).getBundleMessage()))
                .toList();

        return buildErrorResponse(request, errors);
    }

    @ExceptionHandler({
            IdException.class,
            IdNullException.class,
            ResourceFoundException.class,
            TokenExceptions.class
    })
    public ResponseEntity<Map<String, Object>> handleCustomExceptions(Exception ex, HttpServletRequest request) {
        String translatedMessage = BundleTranslatorService.getMessage(ex.getMessage()).getBundleMessage();

        Map<String, String> error = new HashMap<>();
        error.put("field", "system");
        error.put("message", translatedMessage);

        return buildErrorResponse(request, List.of(error));
    }

    private <T> ResponseEntity<Map<String, Object>> buildErrorResponse(HttpServletRequest request, List<T> errors) {
        Map<String, Object> response = new LinkedHashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", 400);
        response.put("bundleMessage", errors);
//        response.put("path", request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }
}
