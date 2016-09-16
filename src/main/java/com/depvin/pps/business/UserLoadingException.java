package com.depvin.pps.business;

public class UserLoadingException extends Exception {
    public UserLoadingException(String message) {
        super(message);
    }
    public UserLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
