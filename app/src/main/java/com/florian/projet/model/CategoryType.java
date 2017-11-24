package com.florian.projet.model;

public class CategoryType {
    private int id;
    private double volume;
    private int rebutInPercent;
    private double rebutInQuantity;

    public CategoryType(int id, double volume, int rebutInPercent, double rebutInQuantity) {
        this.id = id;
        this.volume = volume;
        this.rebutInPercent = rebutInPercent;
        this.rebutInQuantity = rebutInQuantity;
    }

    public int getId() {
        return id;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getRebutInPercent() {
        return rebutInPercent;
    }

    public void setRebutInPercent(int rebutInPercent) {
        this.rebutInPercent = rebutInPercent;
    }

    public double getRebutInQuantity() {
        return rebutInQuantity;
    }

    public void setRebutInQuantity(double rebutInQuantity) {
        this.rebutInQuantity = rebutInQuantity;
    }
}
