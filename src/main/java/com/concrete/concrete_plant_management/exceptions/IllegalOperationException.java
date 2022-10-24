package com.concrete.concrete_plant_management.exceptions;

public class IllegalOperationException extends RuntimeException{
    public IllegalOperationException(final Long id) {
        super(String.format("order with id %s cannot be archived because of undone batches", id));
    }
}
