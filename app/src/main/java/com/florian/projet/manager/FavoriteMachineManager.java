package com.florian.projet.manager;

import com.florian.projet.model.Machine;

public class FavoriteMachineManager {
    private static FavoriteMachineManager instance;

    private DatabaseManager databaseManager;

    private FavoriteMachineManager() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static FavoriteMachineManager getInstance() {
        if(instance == null) {
            instance = new FavoriteMachineManager();
        }
        return instance;
    }

    public void update(Machine machine) {
        databaseManager.updateMachine(machine);
    }

    public void getAllFavMachine(DatabaseManager.GetAllFavTask.Callback callback) {
        databaseManager.getAllFavMachine(callback);
    }
}
