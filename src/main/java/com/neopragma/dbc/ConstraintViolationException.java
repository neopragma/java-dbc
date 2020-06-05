package com.neopragma.dbc;

public class ConstraintViolationException extends RuntimeException {
    private String message;
    public ConstraintViolationException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
