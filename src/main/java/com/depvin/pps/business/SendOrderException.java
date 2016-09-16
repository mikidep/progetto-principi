package com.depvin.pps.business;

public class SendOrderException extends Exception {
    public SendOrderException(String message) {
        super(message);
    }

    public SendOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
