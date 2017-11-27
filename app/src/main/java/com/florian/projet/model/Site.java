package com.florian.projet.model;

public class Site extends CategoryType {
    private String name;

    public Site(int id, String name, double volume, int rebutInPercent, double rebutInQuantity) {
        super(id, volume, rebutInPercent, rebutInQuantity);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
