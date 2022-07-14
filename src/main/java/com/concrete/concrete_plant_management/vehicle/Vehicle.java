package com.concrete.concrete_plant_management.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    private String name;
    private String type;
    @NotBlank
    private String regNo;
    private String description;
    private float capacity;
    private float pumpLength;

    public Vehicle(final String name, final String type, final String regNo, final String description,
                   final float capacity, final float pumpLength) {
        this.name = name;
        this.type = type;
        this.regNo = regNo;
        this.description = description;
        this.capacity = capacity;
        this.pumpLength = pumpLength;
    }
}
