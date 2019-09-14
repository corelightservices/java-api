package com.corelightservices.api;

/// <summary>
/// Base class for API related exceptions thrown by request methods.
/// </summary>
public class CLSApiException extends Exception {
    CLSApiException() {
        super();
    }

    CLSApiException(String message) {
        super(message);
    }

    CLSApiException(String message, Exception innerException) {
        super(message, innerException);
    }
}
