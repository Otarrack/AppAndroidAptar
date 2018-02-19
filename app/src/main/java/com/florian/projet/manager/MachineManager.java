package com.florian.projet.manager;

import com.florian.projet.model.Machine;

import java.util.ArrayList;
import java.util.List;

public class MachineManager {
    private static MachineManager instance;
    private List<Machine> listMachine;

    private MachineManager() {
        setListMachine();
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

    private void setListMachine() {
        listMachine = new ArrayList<>();
        Machine machine;
        for (int i = 1; i < 5; i++) {
            machine = new Machine(i + "",i * 103,i,i + 13*i);
            listMachine.add(machine);
            //TODO: Récupèration des données à partir du manager qui récupère du serveur
        }
    }

    public Machine getMachineAt(int position) {
        if (position < listMachine.size()) {
            return listMachine.get(position);
        } else {
            return null;
        }
    }
}
