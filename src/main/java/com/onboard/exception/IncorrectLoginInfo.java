package com.onboard.exception;

public class IncorrectLoginInfo extends RuntimeException {
    public IncorrectLoginInfo(String message) {
        super(message);
    }
}
