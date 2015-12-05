package com.depvin.pps.dao;

/**
 * Created by Michele De Pascalis on 05/12/15.
 */
public class NoSuchUserException extends Exception {
    public NoSuchUserException(String message) {
        super(message);
    }
    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
