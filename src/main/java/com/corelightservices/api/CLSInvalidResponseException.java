package com.corelightservices.api;

/// <summary>
/// Thrown when the response could not be evaluated.
/// </summary>
public class CLSInvalidResponseException extends CLSApiException {
    CLSInvalidResponseException() {
        super("Invalid response");
    }
}
