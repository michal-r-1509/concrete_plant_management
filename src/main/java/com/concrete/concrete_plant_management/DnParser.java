package com.concrete.concrete_plant_management;

public record DnParser(int id) {

    @Override
    public String toString() {
        String base = "00000";
        String sId = "" + id;
        String result = base.substring(0, base.length() - sId.length());
        return result.concat(sId);
    }
}
