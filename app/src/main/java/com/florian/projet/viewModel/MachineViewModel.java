package com.florian.projet.viewModel;

import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.manager.PourcentagePerfDatabaseManager;
import com.florian.projet.tools.MachineCallback;

import java.util.List;

public class MachineViewModel {
    private static MachineViewModel instance;

    private PourcentagePerfDatabaseManager pourcentagePerfDatabaseManager;
    private Machine currentMachine;

    public static MachineViewModel getInstance() {
        if(instance == null) {
            instance = new MachineViewModel();
        }
        return instance;
    }

    private MachineViewModel() {
        pourcentagePerfDatabaseManager = PourcentagePerfDatabaseManager.getInstance();
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

    public void getMachineBySite(List<Integer> siteList, MachineCallback callback) {
        pourcentagePerfDatabaseManager.getMachineBySite(siteList, callback);
    }
}
