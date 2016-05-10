package com.depvin.pps.business;

/**
 * Created by costantino on 10/05/16.
 */
public class UserLoadingException extends Exception {
    public UserLoadingException(String message) {
        super(message);
    }
    public UserLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
