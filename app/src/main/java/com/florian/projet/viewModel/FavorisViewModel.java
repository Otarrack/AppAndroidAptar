package com.florian.projet.viewModel;

import android.util.Log;

import com.florian.projet.manager.DatabaseManager;
import com.florian.projet.manager.FavoriteMachineManager;
import com.florian.projet.model.Machine;
import com.florian.projet.model.SiteEnum;

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

    public void updateFavMachine(Machine machine) {
        try {
            favoriteMachineManager.update(machine);
        } catch (Exception e) {
            Log.d("Insert Fav View Model", e.getMessage());
        }
    }

    public void getAllFavMachine(DatabaseManager.GetAllFavTask.Callback callback) {
        favoriteMachineManager.getAllFavMachine(callback);
    }
}
