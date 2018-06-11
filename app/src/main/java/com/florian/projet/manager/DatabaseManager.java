package com.florian.projet.manager;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.florian.projet.bdd.database.FavMachineDataBase;
import com.florian.projet.model.Machine;

public class DatabaseManager {

    private static final String TAG = DatabaseManager.class.getName();


    public static void populateAsync(@NonNull final FavMachineDataBase db, int choice) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute(choice);
    }

    private static void addMachine(final FavMachineDataBase db, Machine machine) {
        db.favMachineDao().insertMachine(machine);
    }



    private static class PopulateDbAsync extends AsyncTask<Integer, Void, Void> {

        private final FavMachineDataBase db;

        PopulateDbAsync(FavMachineDataBase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Integer... choice) {

            return null;
        }
    }

}
