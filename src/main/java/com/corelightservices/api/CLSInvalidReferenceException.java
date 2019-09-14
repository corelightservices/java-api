package com.corelightservices.api;

/**
 * Thrown when supplied API reference is invalid.
 */
public class CLSInvalidReferenceException extends CLSApiException {
    CLSInvalidReferenceException() {
        super("API reference is invalid.");
    }
}
