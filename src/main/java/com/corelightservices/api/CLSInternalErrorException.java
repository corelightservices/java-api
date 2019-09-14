package com.corelightservices.api;

/**
 * Thrown when there is an internal error at the CLS server.
 */
public class CLSInternalErrorException extends CLSApiException {
    CLSInternalErrorException() {
        super("Internal error");
    }
}

