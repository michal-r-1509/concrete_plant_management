package com.concrete.concrete_plant_management.archive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "archives")
class ArchivedOrderBatch {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Long id;
    private String dnNo;
    private LocalDate date;
    private LocalTime time;
    private String siteAddress;
    private String clientName;
    private String clientAddress;
    private String clientPostCode;
    private String clientCity;
    private String clientNip;
    private String vehicleType;
    private String vehicleName;
    private String vehicleRegNo;
    private String concreteClass;
    private double amount;

    public ArchivedOrderBatch(final String dnNo, final LocalDate date, final LocalTime time, final String siteAddress,
                              final String clientName, final String clientAddress, final String clientPostCode,
                              final String clientCity, final String clientNip, final String vehicleType,
                              final String vehicleName, final String vehicleRegNo, final String concreteClass,
                              final double amount) {
        this.dnNo = dnNo;
        this.date = date;
        this.time = time;
        this.siteAddress = siteAddress;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientPostCode = clientPostCode;
        this.clientCity = clientCity;
        this.clientNip = clientNip;
        this.vehicleType = vehicleType;
        this.vehicleName = vehicleName;
        this.vehicleRegNo = vehicleRegNo;
        this.concreteClass = concreteClass;
        this.amount = amount;
    }
}
