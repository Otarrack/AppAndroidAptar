package com.florian.projet.model;

public class Site {
    private String name;
    private double volume;
    private double waste;


    public Site(String name, double volume, double waste) {
        this.name = name;
        this.volume = volume;
        this.waste = waste;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getWaste() {
        return waste;
    }

    public void setWaste(double waste) {
        this.waste = waste;
    }
}
