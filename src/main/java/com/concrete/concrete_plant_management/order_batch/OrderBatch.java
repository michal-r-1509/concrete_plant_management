package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.client.Client;
import com.concrete.concrete_plant_management.order.Order;
import com.concrete.concrete_plant_management.vehicle.Vehicle;
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
    private float amount;
    @NotNull(message = "time cannot be null")
    private LocalTime time;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @OneToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    public OrderBatch(final float amount, final LocalTime time, final boolean status, final Order order, final Vehicle vehicle) {
        this.amount = amount;
        this.time = time;
        this.status = status;
        this.order = order;
        this.vehicle = vehicle;
    }
}
