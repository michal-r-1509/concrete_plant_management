package com.concrete.concrete_plant_management.support;

import com.concrete.concrete_plant_management.domain.Order;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderFactory {
    private List<Order> orders = new ArrayList<>();

    public OrderFactory() {
        orders.add(Order.builder()
                .date(LocalDate.of(2022, 1, 10))
                .time(LocalTime.of(10, 0))
                .amount(5.0)
                .concreteClass("C30/37")
                .siteAddress("Jesionowa 4, Krakow")
                .description("podklady")
                .pump(false)
                .done(false)
                .build());
        orders.add(Order.builder()
                .date(LocalDate.of(2022, 1, 10))
                .time(LocalTime.of(15, 0))
                .amount(24.0)
                .concreteClass("C20/25")
                .siteAddress("Jagodowa 47, Wieliczka")
                .description("rynny")
                .pump(true)
                .done(false)
                .build());
        orders.add(Order.builder()
                .date(LocalDate.of(2022, 5, 15))
                .time(LocalTime.of(18, 30))
                .amount(10.0)
                .concreteClass("C16/20")
                .siteAddress("Lipna 22, Miechow")
                .pump(false)
                .done(false)
                .build());
    }

}
