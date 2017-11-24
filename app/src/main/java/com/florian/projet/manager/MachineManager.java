package com.florian.projet.manager;

import com.florian.projet.model.Machine;

import java.util.List;

public class MachineManager {
    private static MachineManager instance;
    private List<Machine> listMachine;

    public MachineManager() {
        setListMachine();
    }

    public static MachineManager getInstance() {
        if(instance == null) {
            instance = new MachineManager();
        }
        return instance;
    }

    public List<Machine> getListMachine() {
        return listMachine;
    }

    private void setListMachine() {
        for (int i = 1; i < 5; i++) {
            Machine site = new Machine(i,i * 103,i,i + 13*i);
            listMachine.add(site);
        }
    }
}
