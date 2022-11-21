package com.example.blog.domain.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationException extends Exception {

    private String message = "Validation error";
    public ValidationException() {
        super("Validation error");
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<ObjectError> errors) {
        super();
        StringBuilder builder = new StringBuilder();
        builder.append("Ошибка валидации");
        for (var error: errors) {
            builder.append(", ").append(error.getDefaultMessage());
        }
        message = builder.toString();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
