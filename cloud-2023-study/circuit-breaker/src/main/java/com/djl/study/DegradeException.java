package com.djl.study;

public class DegradeException extends Exception {

    public DegradeException() {
    }

    public DegradeException(String message) {
        super(message);
    }

    public DegradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DegradeException(Throwable cause) {
        super(cause);
    }

    public DegradeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}