package com.concrete.concrete_plant_management.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    private LocalDate date;
    private LocalTime time;
    private double amount;
    private String concreteClass;
    private String siteAddress;
    private String description;
    private boolean pump;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}