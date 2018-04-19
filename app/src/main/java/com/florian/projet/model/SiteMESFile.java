package com.florian.projet.model;

import java.util.ArrayList;

public class SiteMESFile {
    private String siteName;
    private ArrayList<MachineMESFile> machineList;

    public SiteMESFile(String siteName, ArrayList<MachineMESFile> machineList) {
        this.siteName = siteName;
        this.machineList = machineList;
    }

    public String getSiteName() {
        return siteName;
    }

    public ArrayList<MachineMESFile> getMachineList() {
        return machineList;
    }

    public void addMachineToList(MachineMESFile machineMESFile) {
        machineList.add(machineMESFile);
    }
}
