package com.corelightservices.api;

/**
 * Thrown when the response could not be evaluated.
 */
public class CLSInvalidResponseException extends CLSApiException {
    CLSInvalidResponseException() {
        super("Invalid response");
    }
}
