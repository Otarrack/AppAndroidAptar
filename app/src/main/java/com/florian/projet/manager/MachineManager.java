package com.florian.projet.manager;

import com.florian.projet.model.Machine;

import java.util.ArrayList;
import java.util.List;

public class MachineManager {
    private static MachineManager instance;
    private List<Machine> listMachine;

    private MachineManager() {
        listMachine = new ArrayList<>();
    }

    public static MachineManager getInstance() {
        if(instance == null) {
            instance = new MachineManager();
        }
        return instance;
    }

    public List<Machine> getAllMachine() {
        return listMachine;
    }

    public void addMachine(Machine machine) {
        listMachine.add(machine);
    }

    public Machine getMachineAt(int position) {
        if (position < listMachine.size()) {
            return listMachine.get(position);
        } else {
            return null;
        }
    }
}
