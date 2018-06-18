package com.florian.projet.viewModel;

import com.florian.projet.manager.MESFileManager;
import com.florian.projet.model.Machine;

import java.util.ArrayList;

public class MachineViewModel {
    private static MachineViewModel instance;
    private Machine currentMachine;

    public static MachineViewModel getInstance() {
        if(instance == null) {
            instance = new MachineViewModel();
        }
        return instance;
    }

    private MachineViewModel() {

    }

    public void setCurrentMachine(Machine machine) {
        currentMachine = machine;
    }

    public void delCurrentMachine() {
        currentMachine = null;
    }

    public Machine getCurrentMachine() {
        return currentMachine;
    }
}
