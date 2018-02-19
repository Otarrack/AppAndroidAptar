package com.florian.projet.model;

public class Site {
    private String name;
    private double volume;
    private int rebutInPercent;
    private double rebutInQuantity;

    public Site(String name, double volume, int rebutInPercent, double rebutInQuantity) {
        this.name = name;
        this.volume = volume;
        this.rebutInPercent = rebutInPercent;
        this.rebutInQuantity = rebutInQuantity;
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

    public int getWasteInPercent() {
        return rebutInPercent;
    }

    public void setWasteInPercent(int rebutInPercent) {
        this.rebutInPercent = rebutInPercent;
    }

    public double getWasteInQuantity() {
        return rebutInQuantity;
    }

    public void setWasteInQuantity(double rebutInQuantity) {
        this.rebutInQuantity = rebutInQuantity;
    }
}
