package com.concrete.concrete_plant_management.exceptions;

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
        return String.format("%s with id %d not found", ex.getMessage(), ex.getId());
    }

    @ExceptionHandler(ElementConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String elementConflict(ElementConflictException ex){
        return ex.getMessage() + " already exists";
    }

    @ExceptionHandler(VehicleByRegNoExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String vehicleByRegNoConflict(VehicleByRegNoExistsException ex){
        return String.format("vehicle with registry number %s already exists", ex.getMessage());
    }
}
