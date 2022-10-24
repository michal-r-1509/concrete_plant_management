package com.concrete.concrete_plant_management.archive.tool;

import org.springframework.stereotype.Component;

@Component
public record TaxpayerIdentNoParser() {

    public String toString(long data) {
        String tpIdentNo = String.valueOf(data);
        if (tpIdentNo.length() != 10){
            return "bad 'taxpayer identity number' format";
        }else {
            return tpIdentNo.substring(0,3) + "-" + tpIdentNo.substring(3, 6)
                    + "-" + tpIdentNo.substring(6, 8) + "-" + tpIdentNo.substring(8);
        }
    }
}
