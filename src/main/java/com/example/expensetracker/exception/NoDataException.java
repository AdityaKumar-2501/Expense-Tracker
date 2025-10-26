package com.example.expensetracker.exception;

public class NoDataException extends RuntimeException {
    public NoDataException(String message) {
        super(message);
    }
}
