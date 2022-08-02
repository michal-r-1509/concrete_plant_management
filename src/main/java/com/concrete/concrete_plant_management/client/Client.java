package com.concrete.concrete_plant_management.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
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
    @Min(1000000000L)
    @Max(9999999999L)
    private long nip;

// to delete
    public Client(final String name, final String streetAndNo, final String postCode,
                  final String city, final long nip) {
        this.name = name;
        this.streetAndNo = streetAndNo;
        this.postCode = postCode;
        this.city = city;
        this.nip = nip;
    }

    public Client(final int id) {
        this.id = id;
    }
    //
}
