package com.concrete.concrete_plant_management.exceptions;

public class ElementConflictException extends RuntimeException{

    public ElementConflictException(final String message){
        super(message);
    }

    public ElementConflictException(final String message, final String cause) {
        super(message + " " + cause);
    }

    public ElementConflictException(final String elementType, final String cause, final String causeNumber) {
        super(String.format("%s with %s %s already exists", elementType, cause, causeNumber));
    }
}
