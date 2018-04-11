package com.florian.projet.viewModel;

import com.florian.projet.manager.MachineManager;
import com.florian.projet.model.Machine;

public class MachineViewModel {
    private static MachineViewModel instance;
    private MachineManager machineManager;
    private Machine currentMachine;

    public static MachineViewModel getInstance() {
        if(instance == null) {
            instance = new MachineViewModel();
        }
        return instance;
    }

    private MachineViewModel() {
        machineManager = MachineManager.getInstance();
    }

    public void setCurrentMachine(int id) {
        currentMachine = machineManager.getMachineAt(id);
    }

    public void delCurrentMachine() {
        currentMachine = null;
    }
}
