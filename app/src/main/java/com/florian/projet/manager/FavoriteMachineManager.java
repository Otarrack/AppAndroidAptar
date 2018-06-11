package com.florian.projet.manager;

import com.florian.projet.bdd.database.FavMachineDataBase;
import com.florian.projet.model.Machine;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMachineManager {
    private static FavoriteMachineManager instance;
    private FavMachineDataBase db;

    private FavoriteMachineManager() {
    }

    public static FavoriteMachineManager getInstance() {
        if(instance == null) {
            instance = new FavoriteMachineManager();
        }
        return instance;
    }

    public void setAppQuestionDatabase(FavMachineDataBase db) {
        this.db = db;
    }

    public Boolean insertMachine(Machine machine) {
        long result = db.favMachineDao().insertMachine(machine);
        if (result != 0)
            return true;
        return false;
    }

    public Boolean deleteMachine(Machine machine) {
        int result = db.favMachineDao().deleteMachine(machine);
        if (result != 0)
            return true;
        return false;
    }

    public ArrayList<Machine> getAllFavMachine() {
        List<Machine> machineList = db.favMachineDao().getAll();
        if (machineList.size() > 0) {
            return new ArrayList<>(machineList);
        } else {
            return new ArrayList<>();
        }
    }

}
