package com.posto.gas.constrollers.exceptions;


import com.posto.gas.services.exceptions.DataBaseException;
import com.posto.gas.services.exceptions.DateException;
import com.posto.gas.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> DataBase(DataBaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        // BAD_REQUEST is used here because the error is related to a bad request, such as
        // trying to delete a user and violating a database constraint.
        // This is not a server error, but rather an issue with the request made by the
        // client, hence the use of BAD_REQUEST.
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DateException.class)
    public ResponseEntity<StandardError> Date(DateException e, HttpServletRequest request) {
        String error = "Date error";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        // NOT_ACCEPTABLE is used here because the error is related to an invalid date format
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
