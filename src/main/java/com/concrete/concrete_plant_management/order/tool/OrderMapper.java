package com.concrete.concrete_plant_management.order.tool;

import com.concrete.concrete_plant_management.domain.Client;
import com.concrete.concrete_plant_management.domain.Order;
import com.concrete.concrete_plant_management.order.dto.OrderRequestDTO;
import com.concrete.concrete_plant_management.order.dto.OrderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order newOrderDataValidating(OrderRequestDTO toSave, final Client client){
        return Order.builder()
                .date(toSave.getDate())
                .time(toSave.getTime())
                .amount(toSave.getAmount())
                .concreteClass(toSave.getConcreteClass())
                .siteAddress(toSave.getSiteAddress())
                .description(toSave.getDescription())
                .pump(toSave.isPump())
                .done(false)
                .client(client)
                .build();
    }

    public void existOrderDataValidating(OrderRequestDTO toSave, final Client client, final Order existOrder){
        existOrder.setDate(toSave.getDate());
        existOrder.setTime(toSave.getTime());
        existOrder.setAmount(toSave.getAmount());
        existOrder.setConcreteClass(toSave.getConcreteClass());
        existOrder.setSiteAddress(toSave.getSiteAddress());
        existOrder.setDescription(toSave.getDescription());
        existOrder.setPump(toSave.isPump());
        existOrder.setClient(client);
    }


    public void newOrderDataValidating(Order order){
        order.setAmount(amountValidating(order.getAmount()));
    }

    private double amountValidating(double amount){
        return Math.min(Math.max(amount, 0.0), 1000.0);
    }

    public OrderResponseDTO toResponse(final Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .time(order.getTime())
                .amount(order.getAmount())
                .clientName(order.getClient().getName())
                .siteAddress(order.getSiteAddress())
                .pump(order.isPump())
                .done(order.isDone())
                .build();
    }
}
