package com.florian.projet.model;

import java.util.Date;

public class Article {
    private String id;
    private double volume;
    private int rebutInPercent;
    private double rebutInQuantity;
    private Date dateDeclarationProduction;
    private Date dateStartPlanned;
    private Date dateEndPlanned;

    public Article(String id, double volume, int rebutInPercent, double rebutInQuantity) {
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
