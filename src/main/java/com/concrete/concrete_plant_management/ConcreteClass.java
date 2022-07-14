package com.concrete.concrete_plant_management;

public enum ConcreteClass {

    C8_10("C8/10"),
    C12_15("C12/15"),
    C16_20("C16/20"),
    C20_25("C20/25"),
    C25_30("C25/30"),
    C30_37("C30/37"),
    C35_45("C35/45"),
    C40_50("C40/50");

    private String name;

    ConcreteClass(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
