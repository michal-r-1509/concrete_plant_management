package com.concrete.concrete_plant_management.exceptions;

public class TaxpayerIdentityNumberException extends RuntimeException {
    public TaxpayerIdentityNumberException() {
        super("invalid taxpayer identity number");
    }
}
