package com.shoppingcart.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class CategoryAdvisor {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Error> handleSQLIntegrityConstraintViolationException(
        SQLIntegrityConstraintViolationException ex,
        WebRequest request
    ){
        Error error = new Error();
        error.setMessage("Cannot delete this entity because is relating with another");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
