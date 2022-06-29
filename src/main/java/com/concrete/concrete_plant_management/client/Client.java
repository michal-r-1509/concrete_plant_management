package com.concrete.concrete_plant_management.client;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @NotBlank
    private String name;
    private String streetAndNo;
    private String postCode;
    private String city;
    private long nip;

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getStreetAndNo() {
        return streetAndNo;
    }

    public void setStreetAndNo(final String streetAndNo) {
        this.streetAndNo = streetAndNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(final String postCode) {
        this.postCode = postCode;
    }

    public long getNip() {
        return nip;
    }

    public void setNip(final long nip) {
        this.nip = nip;
    }
}
