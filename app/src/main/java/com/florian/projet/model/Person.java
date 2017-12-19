package com.florian.projet.model;

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
