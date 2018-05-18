package com.florian.projet.quarantaine;

public class Machine {
    private String machineName;
    private double volume;
    private double waste;

    public Machine(String machineName) {
        this.machineName = machineName;
    }

    public Machine(String machineName, double volume, double waste) {
        this.machineName = machineName;
        this.volume = volume;
        this.waste = waste;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
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
