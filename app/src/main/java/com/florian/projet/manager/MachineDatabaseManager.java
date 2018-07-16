package com.florian.projet.manager;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.florian.projet.MyApplication;
import com.florian.projet.R;
import com.florian.projet.bdd.database.MachineDataBase;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.tools.MachineCallback;
import com.florian.projet.tools.SimpleCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MachineDatabaseManager {
    private static MachineDatabaseManager instance;
    MachineDataBase db;

    public MachineDatabaseManager() {
        this.db = MachineDataBase.getInstance();
    }

    /**
     * Singleton qui permet de récupérer l'instance en cours si elle a déjà été créée
     *
     * @return Instance du manager
     */
    public static MachineDatabaseManager getInstance() {
        if (instance == null) {
            instance = new MachineDatabaseManager();
        }

        return instance;
    }

    public void refreshAllMachine(final ArrayList<Machine> machineArrayList, final SimpleCallback insertCallback) {
        deleteAllData(new SimpleCallback() {
            @Override
            public void onSuccess() {
                insertAllMachine(machineArrayList, insertCallback);
            }

            @Override
            public void onFailed(Exception e) {
                insertCallback.onFailed(e);
            }
        });
    }

    public void insertAllMachine(List<Machine> machineList, SimpleCallback callback) {
        try {
            new InsertAllTask(db, callback).execute(machineList.toArray(new Machine[machineList.size()]));

        } catch (Exception e) {
            Log.d("Exception Insert All", e.getMessage());
        }
    }

    public void updateMachine(Machine machine) {
        try {
            Log.d("OZEAEOE", machine.getId() + "");
            new UpdateTask(db).execute(machine);

        } catch (Exception e) {
            Log.d("Exception Update", e.getMessage());
        }
    }

    public void deleteAllData(SimpleCallback callback) {
        new ClearAllTables(db, callback).execute();
    }

    public void getAllMachine(MachineCallback callback) {
        try {
            new GetAllTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Exception Get All", e.getMessage());
        }
    }

    public void getMachineBySite(List<Integer> siteList, MachineCallback callback) {
        try {
            new GetBySiteTask(db, callback).execute(siteList.toArray(new Integer[siteList.size()]));

        } catch (Exception e) {
            Log.d("Exception Get All", e.getMessage());
        }
    }

    public void getAllMachineFav(MachineCallback callback) {
        try {
            new GetAllFavTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Exception Get All Fav", e.getMessage());
        }
    }

    private static class ClearAllTables extends AsyncTask<Void, Void, Void> {

        private final SimpleCallback callback;
        private final MachineDataBase db;
        private Exception exception;

        ClearAllTables(MachineDataBase db, SimpleCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                db.clearAllTables();
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess();
            }
        }
    }

    private static class InsertAllTask extends AsyncTask<Machine, Void, List<Long>> {

        private final MachineDataBase db;
        private SimpleCallback callback;
        private Exception exception;


        InsertAllTask(MachineDataBase db, SimpleCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Long> doInBackground(Machine... machines) {
            try {
                return db.machineDao().insertAll(Arrays.asList(machines));
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Long> result) {
            super.onPostExecute(result);

            if (result == null) {
                callback.onFailed(exception);
            } else if (result.size() > 0) {
                callback.onSuccess();
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.machine_add_all_success,
                        Toast.LENGTH_SHORT).show();
            } else {
                callback.onFailed(new Exception("Aucune machine ajoutée"));
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.machine_add_all_error,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class UpdateTask extends AsyncTask<Machine, Void, Integer> {

        private final MachineDataBase db;

        UpdateTask(MachineDataBase db) {
            this.db = db;
        }

        @Override
        protected Integer doInBackground(Machine... machines) {
            if (machines[0] != null) {
                return db.machineDao().update(machines[0]);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            //if (result > 0) {
            //TODO Voir pour rajouter un callback
        }
    }

    public static class GetAllTask extends AsyncTask<Void, Void, List<Machine>> {

        private final MachineCallback callback;
        private final MachineDataBase db;
        private Exception exception;

        GetAllTask(MachineDataBase db, MachineCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Machine> doInBackground(Void... voids) {
            try {
                return db.machineDao().getAll();
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Machine> machineList) {
            super.onPostExecute(machineList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(machineList);
            }
        }
    }

    public static class GetBySiteTask extends AsyncTask<Integer, Void, List<Machine>> {

        private final MachineCallback callback;
        private final MachineDataBase db;
        private Exception exception;

        GetBySiteTask(MachineDataBase db, MachineCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Machine> doInBackground(Integer... integers) {
            try {
                return db.machineDao().getBySite(Arrays.asList(integers));

            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Machine> machineList) {
            super.onPostExecute(machineList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(machineList);
            }
        }
    }

    public static class GetAllFavTask extends AsyncTask<Void, Void, List<Machine>> {

        private final MachineCallback callback;
        private final MachineDataBase db;
        private Exception exception;

        GetAllFavTask(MachineDataBase db, MachineCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Machine> doInBackground(Void... voids) {
            try {
                return db.machineDao().getAllFav();
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Machine> machineList) {
            super.onPostExecute(machineList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(machineList);
            }
        }
    }

}
