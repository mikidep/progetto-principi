package com.depvin.pps.business;

public class EvasionException extends Exception {
    public EvasionException(String message) {
        super(message);
    }

    public EvasionException(String message, Throwable cause) {
        super(message, cause);
    }
}
