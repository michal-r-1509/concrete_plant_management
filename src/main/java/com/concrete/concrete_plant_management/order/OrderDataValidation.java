package com.concrete.concrete_plant_management.order;

class OrderDataValidation {

    void orderDataValidating(Order order){
        order.setAmount(amountValidating(order.getAmount()));
    }

    private double amountValidating(double amount){
        return Math.min(Math.max(amount, 0.0), 1000.0);
    }
}
