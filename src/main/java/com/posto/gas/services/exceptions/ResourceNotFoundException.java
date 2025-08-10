package com.posto.gas.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private static final String message = "Resource not found. Id: ";

    public ResourceNotFoundException(Object id) {
        super(message + id);
    }
}
