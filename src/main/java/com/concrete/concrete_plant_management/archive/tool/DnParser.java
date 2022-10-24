package com.concrete.concrete_plant_management.archive.tool;

import org.springframework.stereotype.Component;

@Component
public record DnParser() {

    public String toString(Long id) {
        String base = "00000";
        String sId = "" + id;
        String result = base.substring(0, base.length() - sId.length());
        return result.concat(sId);
    }
}
