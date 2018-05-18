package com.florian.projet.viewModel;

import com.florian.projet.manager.MESFileManager;
import com.florian.projet.manager.MachineManager;
import com.florian.projet.model.MachineMESFile;
import com.florian.projet.quarantaine.Machine;

import java.util.ArrayList;

public class MachineViewModel {
    private static MachineViewModel instance;
    private MachineMESFile currentMachine;
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

    public void setCurrentMachine(MachineMESFile machine) {
        currentMachine = machine;
    }

    public void delCurrentMachine() {
        currentMachine = null;
    }

    public ArrayList<MachineMESFile> getAllMachineMES() {
        return mesFileManager.getAllMachineMES();
    }

    public MachineMESFile getCurrentMachine() {
        return currentMachine;
    }
}
