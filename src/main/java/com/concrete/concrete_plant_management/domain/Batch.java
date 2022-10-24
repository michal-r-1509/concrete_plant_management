package com.concrete.concrete_plant_management.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batches")
public class Batch extends BaseEntity {

    private double amount;
    private boolean done;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
