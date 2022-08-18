package com.concrete.concrete_plant_management.exceptions;

public class ElementConflictException extends RuntimeException{

    public ElementConflictException(final String message){
        super(message);
    }

    public ElementConflictException(final String message, final String conflicted) {
        super(message + " " + conflicted);
    }
}
