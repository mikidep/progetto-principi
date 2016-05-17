package com.depvin.pps.business;

/**
 * Created by costantino on 17/05/16.
 */
public class ReportCreationFailedException extends Exception {
    public ReportCreationFailedException(String message) {
        super(message);
    }

    public ReportCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
