package com.fresh.coding.schoolmanagementapi.exceptions;

public class HttpInternalServerException extends RuntimeException {
    public HttpInternalServerException(String message) {
        super(message);
    }
}
