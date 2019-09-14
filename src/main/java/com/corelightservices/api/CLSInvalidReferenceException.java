package com.corelightservices.api;

/// <summary>
/// Thrown when supplied API reference is invalid.
/// </summary>
public class CLSInvalidReferenceException extends CLSApiException {
    CLSInvalidReferenceException() {
        super("API reference is invalid.");
    }
}
