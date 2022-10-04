package com.concrete.concrete_plant_management.domain;

import com.concrete.concrete_plant_management.client.tool.ClientType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends BaseEntity{

    @NotBlank
    @Size(max = 50)
    private String name;
    @Size(max = 50)
    private String streetAndNo;
    @Size(min = 5, max = 6)
    private String postCode;
    @Size(max = 20)
    private String city;
    private long taxpayerIdentNo;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private ClientType type;
}
