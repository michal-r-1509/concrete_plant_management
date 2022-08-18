package com.concrete.concrete_plant_management;

import com.concrete.concrete_plant_management.exceptions.ElementConflictException;
import com.concrete.concrete_plant_management.exceptions.ElementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ElementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String elementNotFoundHandler(ElementNotFoundException ex){
        return ex.getMessage() + " with id " + ex.getId() + " not found";
    }

    @ExceptionHandler(ElementConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String elementConflict(ElementConflictException ex){
        return ex.getMessage() + " already exists";
    }
}
