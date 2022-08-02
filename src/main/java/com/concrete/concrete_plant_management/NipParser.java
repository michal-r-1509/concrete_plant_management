package com.concrete.concrete_plant_management;

public record NipParser(long data) {

    @Override
    public String toString() {
        String nip = String.valueOf(data);
        if (nip.length() != 10){
            return "bad 'nip' format";
        }else {
            return nip.substring(0,3) + "-" + nip.substring(3, 6)
                    + "-" + nip.substring(6, 8) + "-" + nip.substring(8);
        }
    }
}
