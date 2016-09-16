package com.depvin.pps.business;

public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }
    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
