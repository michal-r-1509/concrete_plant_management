package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.order.Order;
import com.concrete.concrete_plant_management.domain.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_batches")
public class OrderBatch {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private int id;
    @Min(0L)
    private double amount;
    @NotNull(message = "time cannot be null")
    private LocalTime time;
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @OneToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    public OrderBatch(final double amount, final LocalTime time, final Order order, final Vehicle vehicle) {
        this.amount = amount;
        this.time = time;
        this.order = order;
        this.vehicle = vehicle;
    }
}
