package com.florian.projet.viewModel;

import android.util.Log;

import com.florian.projet.manager.FavoriteMachineManager;
import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.Machine;
import com.florian.projet.model.SiteEnum;

import java.util.ArrayList;

public class FavorisViewModel {
    private static FavorisViewModel instance;
    private FavoriteMachineManager favoriteMachineManager;

    public static FavorisViewModel getInstance() {
        if(instance == null) {
            instance = new FavorisViewModel();
        }
        return instance;
    }

    private FavorisViewModel() {
        favoriteMachineManager = FavoriteMachineManager.getInstance();
    }

    public void insertMachine(Machine machine) {
        try {
            Log.d("YEDAZ", machine.getMachineName());
            favoriteMachineManager.insertMachine(machine);
        } catch (Exception e) {
            //
        }
    }

    public ArrayList<Machine> getMachineList() {
        Log.d("YEDAZ", favoriteMachineManager.getAllFavMachine().size() +"");
        return favoriteMachineManager.getAllFavMachine();
    }
}
