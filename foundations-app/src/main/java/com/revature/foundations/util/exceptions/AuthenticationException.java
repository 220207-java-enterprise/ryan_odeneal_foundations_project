package com.revature.foundations.util.exceptions;

public class AuthenticationException extends ResourceNotFoundException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException() {
        super("No user found using the provided credentials.");
    }

}