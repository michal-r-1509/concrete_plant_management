package com.concrete.concrete_plant_management.order_batch;

import com.concrete.concrete_plant_management.NipParser;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
class OrderBatchReadModel {
    private final String wz_no;
    private final LocalDate date;
    private final LocalTime time;
    private final String site_address;
    private final String client_and_address;
    private final String vehicle_name;
    private final String vehicle_reg;
    private final String c_class;
    private final float amount;

    @Getter(AccessLevel.NONE)
    private final NipParser nip;

    public OrderBatchReadModel(final OrderBatch orderBatch) {
        nip = new NipParser(orderBatch.getOrder().getClient().getNip());
        this.wz_no = idToString(orderBatch.getId());
        this.date = orderBatch.getOrder().getDate();
        this.time = orderBatch.getTime();
        this.site_address = orderBatch.getOrder().getSiteAddress();
        this.client_and_address = orderBatch.getOrder().getClient().getName() + ", " +
                orderBatch.getOrder().getClient().getStreetAndNo() + ", " +
                orderBatch.getOrder().getClient().getPostCode() + " " +
                orderBatch.getOrder().getClient().getCity() + ", NIP: " + nip;
        this.vehicle_name = orderBatch.getVehicle().getName();
        this.vehicle_reg = orderBatch.getVehicle().getRegNo();
        this.c_class = orderBatch.getOrder().getConcreteClass();
        this.amount = orderBatch.getAmount();
    }

    private String idToString(int id) {
        String base = "00000";
        String sId = "" + id;
        String result = base.substring(0, base.length() - sId.length());
        return result.concat(sId);
    }
}