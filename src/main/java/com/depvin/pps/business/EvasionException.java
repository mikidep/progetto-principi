package com.depvin.pps.business;

/**
 * Created by costantino on 25/08/16.
 */
public class EvasionException extends Exception {
    public EvasionException(String message) {
        super(message);
    }

    public EvasionException(String message, Throwable cause) {
        super(message, cause);
    }
}
