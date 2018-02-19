package com.florian.projet.model;

public class Machine {
    private String id;
    private double volume;
    private int rebutInPercent;
    private double rebutInQuantity;

    public Machine(String id, double volume, int rebutInPercent, double rebutInQuantity) {
        this.id = id;
        this.volume = volume;
        this.rebutInPercent = rebutInPercent;
        this.rebutInQuantity = rebutInQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
