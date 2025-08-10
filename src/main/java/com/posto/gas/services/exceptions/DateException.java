package com.posto.gas.services.exceptions;

public class DateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static final String message = "The wrong Date Format was used, please use this format: ";
    private static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public DateException(String msg) {
        super(message + dateFormat);
    }
}
