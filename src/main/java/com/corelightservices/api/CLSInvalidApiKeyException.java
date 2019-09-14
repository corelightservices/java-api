package com.corelightservices.api;

/// <summary>
/// Thrown when supplied API key is invalid.
/// </summary>
public class CLSInvalidApiKeyException extends CLSApiException {
    CLSInvalidApiKeyException() {
        super("API key is invalid.");
    }
}
