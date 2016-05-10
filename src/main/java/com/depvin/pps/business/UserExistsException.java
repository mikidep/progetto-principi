package com.depvin.pps.business;

/**
 * Created by costantino on 10/05/16.
 */
public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
