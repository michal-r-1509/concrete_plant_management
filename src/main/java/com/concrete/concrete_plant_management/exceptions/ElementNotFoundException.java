package com.concrete.concrete_plant_management.exceptions;

public class ElementNotFoundException extends RuntimeException{
    private final Long id;
    public ElementNotFoundException(final String message, final Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
