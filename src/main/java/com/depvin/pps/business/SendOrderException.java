package com.depvin.pps.business;

/**
 * Created by costantino on 25/08/16.
 */
public class SendOrderException extends Exception {
    public SendOrderException(String message) {
        super(message);
    }

    public SendOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
