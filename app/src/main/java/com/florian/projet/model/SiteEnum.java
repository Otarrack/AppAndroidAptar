package com.florian.projet.model;

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
    private int machineNumber;

    SiteEnum(int siteNum[], String name) {
        this.siteNum = siteNum;
        this.name = name;
        this.machineNumber = 0;
    }

    public int[] getSiteNum() {
        return siteNum;
    }

    public String getName() {
        return name;
    }

    public void addOneToMachineNumber() {
        machineNumber++;
    }

    public int getMachineNumber() {
        return machineNumber;
    }
}
