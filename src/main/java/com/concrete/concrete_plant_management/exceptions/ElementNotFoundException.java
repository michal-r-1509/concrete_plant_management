package com.concrete.concrete_plant_management.exceptions;

public class ElementNotFoundException extends RuntimeException{
    private final int id;
    public ElementNotFoundException(final String message, final int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
