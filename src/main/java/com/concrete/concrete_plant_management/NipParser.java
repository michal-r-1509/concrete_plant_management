package com.concrete.concrete_plant_management;

public record NipParser(long data) {

    @Override
    public String toString() {
        //TODO
        String nip = String.valueOf(data);
        if (nip.length() != 10){
            return "bad 'nip' format";
        }else {
            return nip.substring(0,4) + "-" + nip.substring(4, 7)
                    + "-" + nip.substring(7, 9) + "-" + nip.substring(9);
        }
    }
}
