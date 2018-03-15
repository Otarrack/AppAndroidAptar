package com.florian.projet.model;

public class Site {
    private String siteName;
    private double volume;
    private double waste;

    public Site(String siteName) {
        this.siteName = siteName;
    }

    public Site(String siteName, double volume, double waste) {
        this.siteName = siteName;
        this.volume = volume;
        this.waste = waste;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
