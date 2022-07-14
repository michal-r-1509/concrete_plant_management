package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.ConcreteClass;
import com.concrete.concrete_plant_management.client.Client;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
    @NotNull(message = "date cannot be null")
    private LocalDate date;
    @NotNull(message = "time cannot be null")
    private LocalTime time;
    @Min(1)
    private double amount;
    private String concreteClass;
    private String siteAddress;
    private String description;
    private boolean pump;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Order() {
    }

    public Order(final LocalDate date, final LocalTime time, final double amount, final String concreteClass,
                 final String siteAddress, final String description, final boolean pump, final Client client) {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.concreteClass = concreteClass;
        this.siteAddress = siteAddress;
        this.description = description;
        this.pump = pump;
        this.client = client;
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

    public String getConcreteClass() {
        return concreteClass;
    }

    public void setConcreteClass(final String concreteClass) {
        this.concreteClass = concreteClass;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(final String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public boolean isPump() {
        return pump;
    }

    public void setPump(final boolean pump) {
        this.pump = pump;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(final Client client) {
        this.client = client;
    }
}