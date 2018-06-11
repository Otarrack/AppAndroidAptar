package com.florian.projet.viewModel;

import com.florian.projet.manager.MESFileManager;
import com.florian.projet.model.Machine;

import java.util.ArrayList;

public class MachineViewModel {
    private static MachineViewModel instance;
    private Machine currentMachine;
    private MESFileManager mesFileManager;

    public static MachineViewModel getInstance() {
        if(instance == null) {
            instance = new MachineViewModel();
        }
        return instance;
    }

    private MachineViewModel() {
        mesFileManager = MESFileManager.getInstance();
    }

    public void setCurrentMachine(Machine machine) {
        currentMachine = machine;
    }

    public void delCurrentMachine() {
        currentMachine = null;
    }

    public ArrayList<Machine> getAllMachineMES() {
        return mesFileManager.getAllMachineMES();
    }

    public Machine getCurrentMachine() {
        return currentMachine;
    }
}
