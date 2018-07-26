package com.florian.projet.manager;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.florian.projet.MyApplication;
import com.florian.projet.R;
import com.florian.projet.bdd.database.PourcentagePerformanceDataBase;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.tools.MachineCallback;
import com.florian.projet.tools.SimpleCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PourcentagePerfDatabaseManager {
    private static PourcentagePerfDatabaseManager instance;
    PourcentagePerformanceDataBase db;

    public PourcentagePerfDatabaseManager() {
        this.db = PourcentagePerformanceDataBase.getInstance();
    }

    /**
     * Singleton qui permet de récupérer l'instance en cours si elle a déjà été créée
     *
     * @return Instance du manager
     */
    public static PourcentagePerfDatabaseManager getInstance() {
        if (instance == null) {
            instance = new PourcentagePerfDatabaseManager();
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

    private static class ClearAllTables extends AsyncTask<Void, Void, Void> {

        private final SimpleCallback callback;
        private final PourcentagePerformanceDataBase db;
        private Exception exception;

        ClearAllTables(PourcentagePerformanceDataBase db, SimpleCallback callback) {
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

        private final PourcentagePerformanceDataBase db;
        private SimpleCallback callback;
        private Exception exception;


        InsertAllTask(PourcentagePerformanceDataBase db, SimpleCallback callback) {
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
            } else {
                callback.onFailed(new Exception("Aucune machine ajoutée"));
            }
        }
    }

    public static class GetAllTask extends AsyncTask<Void, Void, List<Machine>> {

        private final MachineCallback callback;
        private final PourcentagePerformanceDataBase db;
        private Exception exception;

        GetAllTask(PourcentagePerformanceDataBase db, MachineCallback callback) {
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
        private final PourcentagePerformanceDataBase db;
        private Exception exception;

        GetBySiteTask(PourcentagePerformanceDataBase db, MachineCallback callback) {
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
}
