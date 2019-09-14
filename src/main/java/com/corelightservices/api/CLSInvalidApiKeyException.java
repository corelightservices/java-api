package com.corelightservices.api;

/**
 * Thrown when supplied API key is invalid.
 */
public class CLSInvalidApiKeyException extends CLSApiException {
    CLSInvalidApiKeyException() {
        super("API key is invalid.");
    }
}
