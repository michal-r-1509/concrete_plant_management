package com.concrete.concrete_plant_management.order;

import com.concrete.concrete_plant_management.client.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
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
    @Min(0)
    private double amount;
    private String concreteClass;
    private String siteAddress;
    private String description;
    private boolean pump;
    @Setter
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

//
    public Order(final LocalDate date, final LocalTime time, final double amount, final String concreteClass,
                 final String siteAddress, final String description, final boolean pump, final boolean status,
                 final Client client) {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.concreteClass = concreteClass;
        this.siteAddress = siteAddress;
        this.description = description;
        this.pump = pump;
        this.status = status;
        this.client = client;
    }

    public Order(final int id) {
        this.id = id;
    }

    //
}