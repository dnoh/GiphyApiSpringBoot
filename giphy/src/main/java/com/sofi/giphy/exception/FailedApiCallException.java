package com.sofi.giphy.exception;

public class FailedApiCallException extends Exception{
    public FailedApiCallException(final String errorMessage) {
        super(errorMessage);
    }
}
