package com.depvin.pps.dao;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(String message) {
        super(message);
    }
    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
