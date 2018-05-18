package com.florian.projet.model;

import com.florian.projet.quarantaine.Machine;

import java.util.List;

public class Person {
    private List<Machine> machineList;

    public Person() {

    }

    public Person(List<Machine> machineList) {
        this.machineList = machineList;
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public void setMachineList(List<Machine> machineList) {
        this.machineList = machineList;
    }
}
