package com.florian.projet.viewModel;

import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.manager.MachineDatabaseManager;

import java.util.List;

public class MachineViewModel {
    private static MachineViewModel instance;

    private MachineDatabaseManager machineDatabaseManager;
    private Machine currentMachine;

    public static MachineViewModel getInstance() {
        if(instance == null) {
            instance = new MachineViewModel();
        }
        return instance;
    }

    private MachineViewModel() {
        machineDatabaseManager = MachineDatabaseManager.getInstance();
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

    public void getMachineBySite(List<Integer> siteList, MachineDatabaseManager.GetBySiteTask.Callback callback) {
        machineDatabaseManager.getMachineBySite(siteList, callback);
    }
}
