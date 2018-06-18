package com.florian.projet.manager;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.florian.projet.MyApplication;
import com.florian.projet.R;
import com.florian.projet.bdd.database.MachineDataBase;
import com.florian.projet.model.Machine;
import com.florian.projet.tools.SimpleCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    MachineDataBase db;

    public DatabaseManager() {
        this.db = MachineDataBase.getInstance();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
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

    public void insertMachine(Machine machine) {
        try {
            new InsertTask(db).execute(machine);

        } catch (Exception e) {
            Log.d("Exception Insert", e.getMessage());
        }
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

    public void deleteMachine(Machine machine) {
        try {
            new DeleteTask(db).execute(machine);

        } catch (Exception e) {
            Log.d("Exception Delete", e.getMessage());
        }
    }

    public void deleteAllData(SimpleCallback callback) {
        new ClearAllTables(db, callback).execute();
    }

    public void getAllMachine(GetAllTask.Callback callback) {
        try {
            new GetAllTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Exception Get All", e.getMessage());
        }
    }

    public void getMachineByName(String name, GetByNameTask.Callback callback) {
        try {
            new GetByNameTask(db, callback).execute(name);

        } catch (Exception e) {
            Log.d("Exception Get All", e.getMessage());
        }
    }

    public void getAllFavMachine(GetAllFavTask.Callback callback) {
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

    private static class InsertTask extends AsyncTask<Machine, Void, Long> {

        private final MachineDataBase db;

        InsertTask(MachineDataBase db) {
            this.db = db;
        }

        @Override
        protected Long doInBackground(Machine... machines) {
            if (machines[0] != null) {
                return db.machineDao().insert(machines[0]);
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);

            if (result > 0) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.add_one_machine_success,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.add_one_machine_error,
                        Toast.LENGTH_SHORT).show();
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
                        R.string.add_all_machine_success,
                        Toast.LENGTH_SHORT).show();
            } else {
                callback.onFailed(new Exception("Aucune machine ajoutée"));
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.add_all_machine_error,
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

    private static class DeleteTask extends AsyncTask<Machine, Void, Integer> {

        private final MachineDataBase db;

        DeleteTask(MachineDataBase db) {
            this.db = db;
        }

        @Override
        protected Integer doInBackground(Machine... machines) {
            if (machines[0] != null) {
                return db.machineDao().delete(machines[0]);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.del_one_machine_success,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.del_one_machine_error,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class GetAllTask extends AsyncTask<Void, Void, List<Machine>> {

        private final GetAllTask.Callback callback;
        private final MachineDataBase db;
        private Exception exception;

        public interface Callback {
            void onSuccess(List<Machine> machineList);

            void onFailed(Exception e);
        }

        GetAllTask(MachineDataBase db, GetAllTask.Callback callback) {
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

    public static class GetByNameTask extends AsyncTask<String, Void, Machine> {

        private final GetByNameTask.Callback callback;
        private final MachineDataBase db;
        private Exception exception;

        public interface Callback {
            void onSuccess(Machine machine);

            void onFailed(Exception e);
        }

        GetByNameTask(MachineDataBase db, GetByNameTask.Callback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected Machine doInBackground(String... strings) {
            try {
                return db.machineDao().getByName(strings[0]);
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Machine machine) {
            super.onPostExecute(machine);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(machine);
            }
        }
    }

    public static class GetAllFavTask extends AsyncTask<Void, Void, List<Machine>> {

        private final GetAllFavTask.Callback callback;
        private final MachineDataBase db;
        private Exception exception;

        public interface Callback {
            void onSuccess(List<Machine> machineList);

            void onFailed(Exception e);
        }

        GetAllFavTask(MachineDataBase db, GetAllFavTask.Callback callback) {
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
