package com.florian.projet.manager;

import com.florian.projet.bdd.entity.Machine;

public class FavoriteMachineManager {
    private static FavoriteMachineManager instance;

    private MachineDatabaseManager machineDatabaseManager;

    private FavoriteMachineManager() {
        machineDatabaseManager = MachineDatabaseManager.getInstance();
    }

    public static FavoriteMachineManager getInstance() {
        if(instance == null) {
            instance = new FavoriteMachineManager();
        }
        return instance;
    }

    public void update(Machine machine) {
        machineDatabaseManager.updateMachine(machine);
    }

    public void getAllFavMachine(MachineDatabaseManager.GetAllFavTask.Callback callback) {
        machineDatabaseManager.getAllFavMachine(callback);
    }
}
