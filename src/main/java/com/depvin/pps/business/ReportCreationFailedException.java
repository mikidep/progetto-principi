package com.depvin.pps.business;

public class ReportCreationFailedException extends Exception {
    public ReportCreationFailedException(String message) {
        super(message);
    }
    public ReportCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
