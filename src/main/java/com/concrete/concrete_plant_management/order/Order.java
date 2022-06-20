package com.concrete.concrete_plant_management.order;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    private boolean status;
    @NotNull(message = "date cannot be null")
    private LocalDate date;
    @NotNull(message = "time cannot be null")
    private LocalTime time;
    private double amount;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(final LocalTime time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public void inverseStatus(){
        this.setStatus(!isStatus());
    }
}
    /*@NotBlank(message = "company name cannot be empty")
    private String company;
    @NotBlank(message = "client name cannot be empty")
    private String nameSurname;
    @NotBlank(message = "client name cannot be empty")
    private String siteAdress;
    */
//@NotNull(message = "concrete class cannot be null")
//private boolean pump;