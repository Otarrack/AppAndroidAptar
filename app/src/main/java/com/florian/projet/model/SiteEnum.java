package com.florian.projet.model;

import java.util.ArrayList;

public enum SiteEnum {
    ALL(new int[]{1, 2, 3, 4}, "Tous"),
    OYO(new int[]{1}, "Oyo - 1"),
    MARTI(new int[]{2}, "Marti - 2"),
    OYO_MARTI(new int[]{1, 2}, OYO + " et " + MARTI),
    GROI(new int[]{3}, "Groi - 3"),
    EVRON(new int[]{4}, "Evron - 4");
    //Pour ajouter un nouveau site, il suffit de l'initialiser ici
    //Il sera alors automatiquement géré par la suite dans l'appli


    private int siteNum[];
    private String name;
    private ArrayList<MachineMESFile> machineMESList;

    SiteEnum(int siteNum[], String name) {
        this.siteNum = siteNum;
        this.name = name;
        this.machineMESList = new ArrayList<>();
    }

    public int[] getSiteNum() {
        return siteNum;
    }

    public String getName() {
        return name;
    }

    public ArrayList<MachineMESFile> getMachineMESList() {
        return machineMESList;
    }

    public void addMachineMESToList(MachineMESFile machineMESFile) {
        this.machineMESList.add(machineMESFile);
    }

    @Override
    public String toString() {
        return name;
    }
}
