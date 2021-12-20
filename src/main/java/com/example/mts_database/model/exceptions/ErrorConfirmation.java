package com.example.mts_database.model.exceptions;

public class ErrorConfirmation extends RuntimeException {
    public ErrorConfirmation(String msg) {
        super(msg);
    }
}
